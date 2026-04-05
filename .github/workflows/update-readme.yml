#!/usr/bin/env python3
"""
generate_readme.py
------------------
Part of Project Discipline | hxrshityadav/DSA
Author  : Harshit Yadav (@hxrshityadav)
Purpose : Scans the repo, collects live metrics, calls NVIDIA's Gemma 4 API
          (google/gemma-4-31b-it), and injects an AI-generated stats block
          into README.md between the markers.

Setup:
    pip install requests
    export NVIDIA_API_KEY="nvapi-your_key_here"

Get your free API key at: https://build.nvidia.com/settings/api-keys
"""

import os
import re
import subprocess
import sys
import textwrap
import requests
from pathlib import Path

# ──────────────────────────────────────────────
# CONSTANTS
# ──────────────────────────────────────────────

REPO_ROOT    = Path(__file__).resolve().parent.parent
README_PATH  = REPO_ROOT / "README.md"
LEETCODE_DIR = REPO_ROOT / "leetcode"
GFG_DIR      = REPO_ROOT / "gfg"

MARKER_START = "<!-- AI-STATS-START -->"
MARKER_END   = "<!-- AI-STATS-END -->"

NVIDIA_API_URL = "https://integrate.api.nvidia.com/v1/chat/completions"
NVIDIA_MODEL   = "google/gemma-4-31b-it"


# ──────────────────────────────────────────────
# METRIC COLLECTION
# ──────────────────────────────────────────────

def count_java_files(directory: Path) -> int:
    if not directory.exists():
        print(f"[WARN] Directory not found, skipping: {directory}")
        return 0
    count = sum(1 for _ in directory.rglob("*.java"))
    print(f"[INFO] Found {count} .java file(s) in '{directory.name}/'")
    return count


def get_commit_count() -> int:
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
# NVIDIA GEMMA API CALL
# ──────────────────────────────────────────────

def build_prompt(metrics: dict) -> str:
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


def call_nvidia_api(prompt: str) -> str:
    api_key = os.environ.get("NVIDIA_API_KEY")
    if not api_key:
        print("[ERROR] NVIDIA_API_KEY environment variable is not set.")
        print("[HINT]  Get a free key at: https://build.nvidia.com/settings/api-keys")
        sys.exit(1)

    headers = {
        "Authorization": f"Bearer {api_key}",
        "Accept": "application/json",
    }

    payload = {
        "model": NVIDIA_MODEL,
        "messages": [{"role": "user", "content": prompt}],
        "max_tokens": 1024,
        "temperature": 0.7,
        "top_p": 0.95,
        "stream": False,
        "chat_template_kwargs": {"enable_thinking": False},
    }

    try:
        print(f"[INFO] Calling NVIDIA API ({NVIDIA_MODEL})...")

        response = requests.post(
            NVIDIA_API_URL,
            headers=headers,
            json=payload,
            timeout=60,
        )

        if response.status_code == 401:
            print("[ERROR] Invalid NVIDIA_API_KEY. Check your GitHub Secret.")
            sys.exit(1)
        elif response.status_code == 429:
            print("[ERROR] NVIDIA rate limit exceeded. Will retry on next push.")
            sys.exit(1)
        elif response.status_code != 200:
            print(f"[ERROR] NVIDIA API returned status {response.status_code}: {response.text}")
            sys.exit(1)

        data = response.json()
        generated_text = data["choices"][0]["message"]["content"].strip()
        generated_text = strip_code_fences(generated_text)

        usage = data.get("usage", {})
        print(
            f"[INFO] API call successful. Tokens used — "
            f"input: {usage.get('prompt_tokens', '?')}, "
            f"output: {usage.get('completion_tokens', '?')}"
        )
        return generated_text

    except requests.exceptions.Timeout:
        print("[ERROR] Request timed out after 60s.")
        sys.exit(1)
    except requests.exceptions.ConnectionError as exc:
        print(f"[ERROR] Network error while calling NVIDIA API: {exc}")
        sys.exit(1)
    except (KeyError, IndexError) as exc:
        print(f"[ERROR] Unexpected API response format: {exc}")
        print(f"[DEBUG] Response: {response.text[:500]}")
        sys.exit(1)


def strip_code_fences(text: str) -> str:
    lines = text.splitlines()
    if lines and lines[0].startswith("```"):
        lines = lines[1:]
    if lines and lines[-1].strip() == "```":
        lines = lines[:-1]
    return "\n".join(lines).strip()


# ──────────────────────────────────────────────
# README INJECTION
# ──────────────────────────────────────────────

def read_readme() -> str:
    if not README_PATH.exists():
        print(f"[ERROR] README.md not found at {README_PATH}")
        sys.exit(1)
    content = README_PATH.read_text(encoding="utf-8")
    print(f"[INFO] README.md loaded ({len(content)} chars)")
    return content


def inject_stats_block(readme_content: str, ai_generated_block: str) -> str:
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

    updated_content, substitution_count = pattern.subn(new_block, readme_content, count=1)

    if substitution_count == 0:
        print("[ERROR] Regex substitution failed — no markers replaced.")
        sys.exit(1)

    print("[INFO] Stats block injected successfully.")
    return updated_content


def write_readme(content: str) -> None:
    README_PATH.write_text(content, encoding="utf-8")
    print(f"[INFO] README.md updated at {README_PATH}")


# ──────────────────────────────────────────────
# MAIN
# ──────────────────────────────────────────────

def main() -> None:
    print("=" * 60)
    print("  Project Discipline — README Auto-Updater (Gemma 4)")
    print("  repo: hxrshityadav/DSA")
    print("=" * 60)

    metrics        = collect_metrics()
    prompt         = build_prompt(metrics)
    ai_block       = call_nvidia_api(prompt)

    print("[INFO] AI-generated block preview:")
    print("─" * 40)
    print(ai_block)
    print("─" * 40)

    readme_content = read_readme()
    updated_readme = inject_stats_block(readme_content, ai_block)
    write_readme(updated_readme)

    print("[DONE] Project Discipline README update complete. ✅")


if __name__ == "__main__":
    main()
