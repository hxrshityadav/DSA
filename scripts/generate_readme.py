#!/usr/bin/env python3
"""
generate_readme.py
------------------
Part of Project Discipline | hxrshityadav/DSA
Author  : Harshit Yadav (@hxrshityadav)
Purpose : Scans the repo, collects live metrics, calls the Anthropic API
          (claude-opus-4-5), and injects an AI-generated stats block back
          into README.md between the markers.
"""

import os
import re
import subprocess
import sys
import textwrap
from pathlib import Path

import anthropic

# ──────────────────────────────────────────────
# CONSTANTS
# ──────────────────────────────────────────────

REPO_ROOT    = Path(__file__).resolve().parent.parent
README_PATH  = REPO_ROOT / "README.md"
LEETCODE_DIR = REPO_ROOT / "leetcode"
GFG_DIR      = REPO_ROOT / "gfg"

MARKER_START = "<!-- AI-STATS-START -->"
MARKER_END   = "<!-- AI-STATS-END -->"

ANTHROPIC_MODEL = "claude-opus-4-5"


# ──────────────────────────────────────────────
# METRIC COLLECTION
# ──────────────────────────────────────────────

def count_java_files(directory: Path) -> int:
    """
    Recursively count all .java files inside the given directory.
    Returns 0 gracefully if the directory does not exist yet.
    """
    if not directory.exists():
        print(f"[WARN] Directory not found, skipping: {directory}")
        return 0

    count = sum(1 for _ in directory.rglob("*.java"))
    print(f"[INFO] Found {count} .java file(s) in '{directory.name}/'")
    return count


def get_commit_count() -> int:
    """
    Run git rev-list --count HEAD to get the total commit count.
    Falls back to 0 on any subprocess error.
    """
    try:
        result = subprocess.run(
            ["git", "rev-list", "--count", "HEAD"],
            cwd=REPO_ROOT,
            capture_output=True,
            text=True,
            check=True,
        )
        count = int(result.stdout.strip())
        print(f"[INFO] Total git commits: {count}")
        return count
    except subprocess.CalledProcessError as exc:
        print(f"[ERROR] git rev-list failed: {exc.stderr.strip()}")
        return 0
    except ValueError as exc:
        print(f"[ERROR] Could not parse commit count: {exc}")
        return 0


def collect_metrics() -> dict:
    """
    Aggregate all live repo metrics into a single dictionary.
    """
    leet_count     = count_java_files(LEETCODE_DIR)
    gfg_count      = count_java_files(GFG_DIR)
    total_problems = leet_count + gfg_count
    commit_count   = get_commit_count()

    metrics = {
        "leetcode_problems": leet_count,
        "gfg_problems"     : gfg_count,
        "total_problems"   : total_problems,
        "commit_count"     : commit_count,
        "language"         : "Java (100%)",
        "owner"            : "hxrshityadav",
        "repo"             : "hxrshityadav/DSA",
    }

    print(f"[INFO] Metrics collected → {metrics}")
    return metrics


# ──────────────────────────────────────────────
# ANTHROPIC API CALL
# ──────────────────────────────────────────────

def build_prompt(metrics: dict) -> str:
    """
    Build the prompt sent to Claude.
    """
    prompt = textwrap.dedent(f"""
        You are the AI engine behind "Project Discipline", a personal branding
        system for competitive programmer Harshit Yadav (@hxrshityadav).

        Your job is to generate a GitHub-Flavored Markdown stats block that will
        be injected directly into the README.md of the repository
        hxrshityadav/DSA. The block must:

        1. Contain a clean Markdown TABLE with the following live metrics
           (use these exact numbers — they were computed in real time):

           - Total Git Commits     : {metrics['commit_count']}
           - Problems on LeetCode  : {metrics['leetcode_problems']}
           - Problems on GFG       : {metrics['gfg_problems']}
           - Total Problems Solved : {metrics['total_problems']}
           - Primary Language      : {metrics['language']}
           - Sync Method (LC)      : Auto-Synced via LeetSync
           - Sync Method (GFG)     : Auto-Synced via GFG-to-GitHub
           - Streak Status         : Active

        2. Include a single-line blockquote BELOW the table that serves as a
           dynamic motivational tagline. The tagline MUST embed the exact commit
           count ({metrics['commit_count']}) and problem count
           ({metrics['total_problems']}) using this template style:
           > "X commits. Y problems. 0 excuses."

        RULES (follow strictly):
        - Output ONLY raw GitHub-Flavored Markdown.
        - Do NOT wrap output in triple backticks or any code fence.
        - Do NOT include any explanations, preamble, or trailing commentary.
        - Use bold (**value**) for all numeric values in the table.
        - The table header row must be: | Metric | Value |
        - Emoji in the Metric column are encouraged for visual flair.
        - Keep the entire block under 25 lines.
    """).strip()

    return prompt


def call_anthropic_api(prompt: str) -> str:
    """
    Call the Anthropic Messages API using claude-opus-4-5.
    """
    api_key = os.environ.get("ANTHROPIC_API_KEY")
    if not api_key:
        print("[ERROR] ANTHROPIC_API_KEY environment variable is not set.")
        sys.exit(1)

    client = anthropic.Anthropic(api_key=api_key)

    try:
        print("[INFO] Calling Anthropic API (claude-opus-4-5)...")
        message = client.messages.create(
            model=ANTHROPIC_MODEL,
            max_tokens=1024,
            messages=[
                {
                    "role": "user",
                    "content": prompt,
                }
            ],
        )

        if not message.content:
            print("[ERROR] Anthropic API returned an empty content list.")
            sys.exit(1)

        generated_text = message.content[0].text.strip()
        print(
            f"[INFO] API call successful. Tokens used — "
            f"input: {message.usage.input_tokens}, "
            f"output: {message.usage.output_tokens}"
        )
        return generated_text

    except anthropic.AuthenticationError:
        print("[ERROR] Invalid ANTHROPIC_API_KEY. Check your GitHub Secret.")
        sys.exit(1)
    except anthropic.RateLimitError:
        print("[ERROR] Anthropic rate limit hit. Will retry on next push.")
        sys.exit(1)
    except anthropic.APIConnectionError as exc:
        print(f"[ERROR] Network error while calling Anthropic API: {exc}")
        sys.exit(1)
    except anthropic.APIStatusError as exc:
        print(f"[ERROR] Anthropic API status {exc.status_code}: {exc.message}")
        sys.exit(1)


# ──────────────────────────────────────────────
# README INJECTION
# ──────────────────────────────────────────────

def read_readme() -> str:
    """Read the current README.md content."""
    if not README_PATH.exists():
        print(f"[ERROR] README.md not found at {README_PATH}")
        sys.exit(1)

    content = README_PATH.read_text(encoding="utf-8")
    print(f"[INFO] README.md loaded ({len(content)} chars)")
    return content


def inject_stats_block(readme_content: str, ai_generated_block: str) -> str:
    """
    Replace everything between the AI-STATS markers with the
    AI-generated block, keeping the markers themselves intact.
    """
    if MARKER_START not in readme_content:
        print(f"[ERROR] Marker '{MARKER_START}' not found in README.md")
        sys.exit(1)

    if MARKER_END not in readme_content:
        print(f"[ERROR] Marker '{MARKER_END}' not found in README.md")
        sys.exit(1)

    new_block = f"{MARKER_START}\n\n{ai_generated_block}\n\n{MARKER_END}"

    pattern = re.compile(
        rf"{re.escape(MARKER_START)}.*?{re.escape(MARKER_END)}",
        flags=re.DOTALL,
    )

    updated_content, substitution_count = pattern.subn(
        new_block, readme_content, count=1
    )

    if substitution_count == 0:
        print("[ERROR] Regex substitution failed — no markers replaced.")
        sys.exit(1)

    print("[INFO] Stats block injected successfully.")
    return updated_content


def write_readme(content: str) -> None:
    """Write the updated content back to README.md."""
    README_PATH.write_text(content, encoding="utf-8")
    print(f"[INFO] README.md updated at {README_PATH}")


# ──────────────────────────────────────────────
# MAIN
# ──────────────────────────────────────────────

def main() -> None:
    print("=" * 60)
    print("  Project Discipline — README Auto-Updater")
    print("  repo: hxrshityadav/DSA")
    print("=" * 60)

    metrics      = collect_metrics()
    prompt       = build_prompt(metrics)
    ai_block     = call_anthropic_api(prompt)

    print("[INFO] AI-generated block preview:")
    print("─" * 40)
    print(ai_block)
    print("─" * 40)

    readme_content  = read_readme()
    updated_readme  = inject_stats_block(readme_content, ai_block)
    write_readme(updated_readme)

    print("[DONE] Project Discipline README update complete. ✅")


if __name__ == "__main__":
    main()