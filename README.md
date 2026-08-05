# 🧠 Data Structures and Algorithms

<img width="960" height="400" alt="17174429-34d2-4976-a625-f4ed5e105ae9" src="https://github.com/user-attachments/assets/586f9523-01a8-44f2-992d-f4e533f7180e" />

---

## 📘 Overview

This project is designed to be **complete, solid, and beginner-friendly**, while also being **clear and well-structured**.

It’s divided into two main parts:

1. **Fundamentals & Theory**  
   → Explains all core concepts of data structures and algorithms in depth.  
2. **Implementation & Practice**  
   → Focuses on coding exercises and problem-solving with multiple approaches.

> 💡 As a junior programmer aiming to become a professional, it’s important to understand that there’s **more than one way to solve a problem**.  
> This project encourages exploring problems from **different perspectives** to strengthen both your logic and coding skills.

---

## ⚙️ Tech Stack

| Tool / Language | Version | Purpose |
|------------------|----------|----------|
| ☕ **Java** | 21.0.9 (LTS) | Core language used for implementation |
| 🧩 **Apache Maven** | 3.9.16 | Build automation and dependency management |
| ✅ **JUnit 5** | 5.11.4 | Tests for every implementation |

---

## 🗂️ Project Structure

```
docs/                                  theory notes, one per topic (start here)
src/main/java/com/github/codemaster/
  fundamentals/
    complexity/    Big O, growth rates, a timing harness
    arrays/        DynamicArray (ArrayList from scratch), ArrayOps
    strings/       two pointers, frequency counting, KMP
    linkedlist/    singly and doubly linked, reversal, Floyd's cycle detection
    stack/         array and linked stacks, MinStack, bracket matching
    queue/         circular buffer queue, linked queue, deque
    hashing/       hash map by chaining and by open addressing
    tree/          BST, traversals, self-balancing AVL tree, trie
    heap/          binary heap / priority queue
    graph/         adjacency list, BFS/DFS, Dijkstra, topological sort, union-find
    searching/     binary search and its variants
    sorting/       bubble, selection, insertion, merge, quick, heap, counting
    recursion/     recursion basics, backtracking (subsets, permutations, n-queens)
    dp/            memoization vs tabulation, the classic problems
  patterns/        two pointers, sliding window, prefix sums
  leetcode/        ← empty on purpose: your own solutions go here
src/test/java/...                      JUnit tests mirroring every package
```

Every public method states its **time and space complexity** in its Javadoc, and every package has a `package-info.java` summary that shows up on hover in the IDE.

---

## 🚀 Getting Started

```bash
mvn test                        # compile and run the whole suite
mvn test -Dtest=SortingTest     # one topic at a time
mvn -q compile                  # compile only
```

Requires JDK 21 or newer.

---

## 📖 The Notes

Long-form theory lives in [`docs/`](docs/README.md) — the idea, the cost, and the mistakes that actually happen.

| # | Note |
|---|------|
| 00 | [How to solve a problem](docs/00-how-to-solve-problems.md) |
| 01 | [Complexity analysis](docs/01-complexity-analysis.md) |
| 02 | [Arrays and strings](docs/02-arrays-and-strings.md) |
| 03 | [Linked lists](docs/03-linked-lists.md) |
| 04 | [Stacks, queues and deques](docs/04-stacks-queues-deques.md) |
| 05 | [Hashing](docs/05-hashing.md) |
| 06 | [Trees](docs/06-trees.md) |
| 07 | [Heaps and priority queues](docs/07-heaps.md) |
| 08 | [Graphs](docs/08-graphs.md) |
| 09 | [Searching and sorting](docs/09-searching-and-sorting.md) |
| 10 | [Recursion and backtracking](docs/10-recursion-and-backtracking.md) |
| 11 | [Dynamic programming](docs/11-dynamic-programming.md) |
| 12 | [Pattern cheat sheet](docs/12-patterns-cheatsheet.md) |
| 13 | [LeetCode workflow](docs/13-leetcode-workflow.md) |

**How to use it:** read the note → read the code with the note open → read the test → delete a method body and rewrite it from scratch. `mvn test` tells you whether you were right.

---

## 🧩 Project Goals

- Build a **strong foundation** in Data Structures and Algorithms.  
- Write **clean, efficient, and maintainable** Java code.  
- Understand **time and space complexity** for each solution.  
- Explore **multiple implementations** (iterative, recursive, optimized, etc.).  
- Prepare for **technical interviews** and **real-world problem-solving**.

---

## 📚 Topics Covered

- Arrays & Strings  
- Linked Lists  
- Stacks, Queues & Deques  
- Trees & Binary Search Trees (plus AVL and tries)  
- Graphs (BFS, DFS, Dijkstra, topological sort, union-find)  
- Sorting & Searching Algorithms  
- Recursion, Backtracking & Dynamic Programming  
- Hashing & HashMaps  
- Heaps & Priority Queues  
- Complexity Analysis (Big O Notation)  
- Problem-solving patterns (two pointers, sliding window, prefix sums)

---

## 🚀 Future Enhancements

- ✅ Add **JUnit tests** for all implementations  
- ✅ Include **visual explanations** (diagrams & charts)  
- ✅ Implement **advanced algorithms**  
- ✅ Add **competitive programming problems** with detailed solutions  

---

## 🧠 Mindset Behind This Project

> “The goal isn’t just to learn algorithms — it’s to **think like a problem solver**.”  
> Every concept here is explained with both **theory and practice**, so you can master DSA from the ground up.

---

## 🧑‍💻 Author

**Amirali Abbasi**  
📍 Java Backend Development, Exited about Infrastructral & Cloud Engineering.  
💼 *Building strong foundations for professional-level software engineering.*

---
