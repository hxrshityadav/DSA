# Contributing Guide

> **For:** Harshit Yadav (@hxrshityadav)
> **Scope:** Personal DSA repository `hxrshityadav/DSA`
> **Philosophy:** Every line of code you commit is a public declaration of your standard.

---

## 1. Folder Naming — Chrome Extension Compatibility

Your two sync extensions write to this repo automatically. Understanding their
naming rules prevents broken paths and failed GitHub Actions.

### LeetSync → `leetcode/`

| Rule | Detail |
|------|--------|
| **Root folder** | Always `leetcode/` — do **not** rename or restructure this |
| **Sub-folder name** | LeetSync uses the problem's URL slug verbatim |
| **Format** | `kebab-case` (all lowercase, spaces → hyphens) |
| **Example** | `leetcode/two-sum/Solution.java` |
| **File name** | Always `Solution.java` — never rename post-sync |
| ⚠️ **Do not** | Manually move files inside `leetcode/` — it breaks sync history |

### GFG-to-GitHub → `gfg/`

| Rule | Detail |
|------|--------|
| **Root folder** | Always `gfg/` — do **not** rename |
| **Sub-folder name** | Derived from the GFG problem title |
| **Format** | `kebab-case` derived from the problem title |
| **Example** | `gfg/reverse-a-linked-list/Solution.java` |
| **File name** | Always `Solution.java` |
| ⚠️ **Do not** | Edit GFG folder names post-sync — the extension will create duplicates |

### Safe Manual Operations

```bash
# ✅ SAFE: Read files
cat leetcode/two-sum/Solution.java

# ✅ SAFE: Add a new manual solution (match the convention exactly)
mkdir -p leetcode/my-custom-problem
touch leetcode/my-custom-problem/Solution.java

# ❌ UNSAFE: Renaming synced folders
mv leetcode/two-sum leetcode/TwoSum   # breaks sync + commit history