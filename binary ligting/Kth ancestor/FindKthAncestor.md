# What is Binary Lifting?

Binary Lifting is a technique used on trees to efficiently find ancestors of nodes.

It is commonly used to answer queries such as:

- Find the k-th ancestor of a node.
- Find the Lowest Common Ancestor (LCA) of two nodes.
- Find the distance between two nodes.

---

# Without Binary Lifting

Before finding ancestors, we first need to know the immediate parent of every node.

This can be done using DFS.

```java
void dfs(int node, int parent)
{
    par[node] = parent;

    for(int child : adj.get(node))
    {
        if(child != parent)
            dfs(child, node);
    }
}
```

Start DFS from the root:

```java
dfs(root, -1);
```

This creates:

```text
par[node] = immediate parent of node
```

Time Complexity:

```text
O(n)
```

since every node is visited once.

---

# Finding k-th Ancestor Using Only Parent Array

Once the parent array is built, we can move upwards one level at a time.

```java
while(k > 0 && node != -1)
{
    node = par[node];
    k--;
}
```

Example:

```text
5 -> 4 -> 3 -> 2 -> 1
```

If we want the 4th ancestor of 5:

```text
5 -> 4
4 -> 3
3 -> 2
2 -> 1
```

We perform 4 jumps.

If k = 100000, we perform 100000 jumps.

Time Complexity:

```text
O(k)
```

per query.







# How Did We Reach Binary Lifting?

We already have a parent array.

```java
par[node] = immediate parent of node;
```

Using this, we can find the k-th ancestor.

```java
while(k > 0)
{
    node = par[node];
    k--;
}
```

Time Complexity:

```text
O(k)
```

---

# What Is Wrong With This?

Suppose:

```text
k = 100000
```

To find the 100000-th ancestor, we perform 100000 jumps.

```text
node -> parent
     -> parent
     -> parent
     -> ...
```

Even though moving to a parent is O(1), doing it 100000 times is expensive.

We need a way to move multiple levels in a single jump.

---

# First Thought

Instead of storing only:

```text
parent[node]
```

Can we store:

```text
2nd ancestor
3rd ancestor
4th ancestor
5th ancestor
...
```

for every node?

Example:

```text
ancestor[node][1]
ancestor[node][2]
ancestor[node][3]
ancestor[node][4]
...
```

---

# Why Is This Bad?

Suppose:

```text
n = 100000
```

A node can have up to 100000 ancestors.

Storing all ancestors for all nodes requires:

```text
O(n²)
```

space.

This is impossible for large trees.

---

# Observation

When finding the k-th ancestor, we do not actually need every ancestor.

We only need a way to represent any number k efficiently.

For example:

```text
9 = 8 + 1
```

If we can jump:

```text
8 levels
1 level
```

we can reach the 9-th ancestor.

Similarly:

```text
13 = 8 + 4 + 1
```

If we can jump:

```text
8 levels
4 levels
1 level
```

we can reach the 13-th ancestor.

---

# Why Powers Of 2?

Every positive integer has a unique binary representation.

Example:

```text
13 = 1101₂

13 = 2³ + 2² + 2⁰
   = 8 + 4 + 1
```

Similarly:

```text
14 = 1110₂

14 = 2³ + 2² + 2¹
   = 8 + 4 + 2
```

Therefore, if we can jump:

```text
1 level
2 levels
4 levels
8 levels
16 levels
...
```

we can construct ANY jump length.

This is why Binary Lifting stores powers of 2 ancestors.

---

# What Should We Store?

For every node:

```text
1 ancestor away
2 ancestors away
4 ancestors away
8 ancestors away
16 ancestors away
...
```

Instead of:

```text
1st ancestor
2nd ancestor
3rd ancestor
4th ancestor
5th ancestor
...
```

---

# Why Do We Need A 2D Array?

For every node, we need information about multiple jump lengths.

Example:

For node 4:

```text
1 jump away  -> 2
2 jumps away -> 1
4 jumps away -> -1
8 jumps away -> -1
```

One node stores many ancestors.

Therefore one array is not enough.

We need:

```text
up[node][power]
```

where:

```text
up[node][0]
= 2⁰-th ancestor

up[node][1]
= 2¹-th ancestor

up[node][2]
= 2²-th ancestor

up[node][3]
= 2³-th ancestor
```

Example:

```text
up[4][0] = 2
up[4][1] = 1
up[4][2] = -1
up[4][3] = -1
```

---

# Meaning Of up[node][i]

The value:

```text
up[node][i]
```

means:

```text
The 2^i-th ancestor of node.
```

Examples:

```text
up[4][0]
```

means:

```text
1st ancestor of 4
```

```text
up[4][1]
```

means:

```text
2nd ancestor of 4
```

```text
up[4][2]
```

means:

```text
4th ancestor of 4
```

```text
up[4][3]
```

means:

```text
8th ancestor of 4
```

This is the core idea behind Binary Lifting.















# Example: Finding the 5th Ancestor

Consider the following tree:

```text
                 1
               /   \
              2     3
             / \
            4   5
           /
          6
         /
        7
       /
      8
     /
    9
```

Suppose:

```text
node = 9
k = 5
```

We want the 5th ancestor of node 9.

---

# Using Only Parent Array

Parent relationships:

```text
par[9] = 8
par[8] = 7
par[7] = 6
par[6] = 4
par[4] = 2
par[2] = 1
```

Move upward one step at a time:

```text
9 -> 8  (1 jump)
8 -> 7  (2 jumps)
7 -> 6  (3 jumps)
6 -> 4  (4 jumps)
4 -> 2  (5 jumps)
```

Answer:

```text
5th ancestor of 9 = 2
```

Time Complexity:

```text
O(5)
```

In general:

```text
O(k)
```

---

# Can We Do Better?

Observe:

```text
5 = 4 + 1
  = 2² + 2⁰
```

Instead of taking:

```text
1 + 1 + 1 + 1 + 1
```

jumps,

we can take:

```text
4 jumps + 1 jump
```

This is the core idea behind Binary Lifting.

---

# What Should We Store?

For every node:

```text
up[node][0] = 1 ancestor away
up[node][1] = 2 ancestors away
up[node][2] = 4 ancestors away
up[node][3] = 8 ancestors away
...
```

For node 9:

```text
up[9][0] = 8
up[9][1] = 7
up[9][2] = 4
up[9][3] = -1
```

Meaning:

```text
up[9][0] -> 1 jump
up[9][1] -> 2 jumps
up[9][2] -> 4 jumps
up[9][3] -> 8 jumps
```

---

# Finding The 5th Ancestor Using Binary Lifting

Binary representation:

```text
5 = 101₂
```

which means:

```text
5 = 2² + 2⁰
  = 4 + 1
```

Start from:

```text
node = 9
```

Take the largest jump first:

```text
9 --(4 jumps)--> 4
```

using:

```text
up[9][2]
```

Then take:

```text
4 --(1 jump)--> 2
```

using:

```text
up[4][0]
```

Final answer:

```text
2
```

Only 2 jumps were needed.

---

# Why Powers Of 2?

Suppose we want:

```text
13th ancestor
```

Binary representation:

```text
13 = 1101₂
   = 8 + 4 + 1
```

So we perform:

```text
8-jump
4-jump
1-jump
```

Similarly:

```text
14 = 1110₂
   = 8 + 4 + 2
```

So we perform:

```text
8-jump
4-jump
2-jump
```

Every integer can be uniquely represented using powers of 2.

Therefore, storing only:

```text
1 jump
2 jumps
4 jumps
8 jumps
16 jumps
...
```

is enough to construct ANY k-length jump.

This is the reason Binary Lifting uses powers of 2 and stores information in a 2D array:

```text
up[node][i]
```

where:

```text
up[node][i] = 2^i-th ancestor of node
```















# Building The Binary Lifting Table

We already know:

```text
up[node][i]
=
2^i-th ancestor of node
```

The question is:

```text
How do we calculate up[node][i]?
```

---

# Key Observation

Suppose we want:

```text
up[node][3]
```

By definition:

```text
up[node][3]
=
8th ancestor of node
```

because:

```text
2³ = 8
```

Instead of finding the 8th ancestor directly, notice:

```text
8 = 4 + 4
```

Therefore:

```text
8th ancestor
=
4th ancestor of the 4th ancestor
```

This gives:

```text
up[node][3]
=
up[ up[node][2] ][2]
```

Similarly:

```text
up[node][2]
=
up[ up[node][1] ][1]
```

because:

```text
4 = 2 + 2
```

And:

```text
up[node][1]
=
up[ up[node][0] ][0]
```

because:

```text
2 = 1 + 1
```

---

# General Formula

Since:

```text
2^j
=
2^(j-1) + 2^(j-1)
```

we get:

```text
up[node][j]
=
up[
      up[node][j-1]
   ][j-1]
```

This is the most important recurrence relation in Binary Lifting.

---

# Example Tree

Consider:

```text
a
|
b
|
c
|
d
|
e
```

Parents:

```text
parent[a] = -1
parent[b] = a
parent[c] = b
parent[d] = c
parent[e] = d
```

---

# Step 1: Fill Column 0

Column 0 stores:

```text
2⁰-th ancestor
=
1st ancestor
=
immediate parent
```

| Node | up[node][0] |
|--------|--------|
| a | -1 |
| b | a |
| c | b |
| d | c |
| e | d |

---

# Step 2: Fill Column 1

Column 1 stores:

```text
2¹-th ancestor
=
2nd ancestor
```

Using:

```text
up[node][1]
=
up[up[node][0]][0]
```

For node e:

```text
up[e][1]
=
up[d][0]
=
c
```

For node d:

```text
up[d][1]
=
up[c][0]
=
b
```

For node c:

```text
up[c][1]
=
up[b][0]
=
a
```

Table:

| Node | up[node][0] | up[node][1] |
|--------|--------|--------|
| a | -1 | -1 |
| b | a | -1 |
| c | b | a |
| d | c | b |
| e | d | c |

---

# Step 3: Fill Column 2

Column 2 stores:

```text
2²-th ancestor
=
4th ancestor
```

Using:

```text
up[node][2]
=
up[up[node][1]][1]
```

For node e:

```text
up[e][2]
=
up[c][1]
=
a
```

For node d:

```text
up[d][2]
=
up[b][1]
=
-1
```

Table:

| Node | 2⁰ | 2¹ | 2² |
|--------|--------|--------|--------|
| a | -1 | -1 | -1 |
| b | a | -1 | -1 |
| c | b | a | -1 |
| d | c | b | -1 |
| e | d | c | a |

---

# Visual Meaning

For node e:

```text
up[e][0] = d
```

```text
e -> d
```

1 jump.

---

```text
up[e][1] = c
```

```text
e -> d -> c
```

2 jumps.

---

```text
up[e][2] = a
```

```text
e -> d -> c -> b -> a
```

4 jumps.

---

# Why Is The Table 2D?

A single node needs multiple ancestors.

For node e:

```text
1st ancestor
2nd ancestor
4th ancestor
8th ancestor
16th ancestor
...
```

Therefore we need:

```text
up[node][power]
```

Rows:

```text
Nodes
```

Columns:

```text
0,1,2,3,...
```

representing:

```text
2⁰
2¹
2²
2³
...
```

ancestor jumps.

---

# Building Code

```java
for(int j = 1; j < LOG; j++)
{
    for(int node = 1; node <= n; node++)
    {
        if(up[node][j - 1] != -1)
        {
            up[node][j]
            =
            up[
                up[node][j - 1]
              ][j - 1];
        }
    }
}
```

---

# Dry Run

Suppose:

```text
node = e
j = 2
```

Need:

```text
up[e][2]
```

First:

```text
up[e][1]
=
c
```

Now substitute:

```text
up[e][2]
=
up[c][1]
```

And:

```text
up[c][1]
=
a
```

Therefore:

```text
up[e][2]
=
a
```

which is indeed the 4th ancestor of e.

---

# Final Intuition

To find a 2^j-th ancestor:

1. Move 2^(j-1) steps upward.
2. From there, move another 2^(j-1) steps upward.

This leads to:

```text
up[node][j]
=
up[up[node][j-1]][j-1]
```

which is the foundation of Binary Lifting.







