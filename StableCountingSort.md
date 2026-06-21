# Stable Counting Sort

## Where Does Counting Sort Fit?

Most sorting algorithms work by comparing elements.

Examples:

* Bubble Sort
* Selection Sort
* Quick Sort
* Merge Sort

These algorithms repeatedly ask questions like:

```java
5 > 2 ?
8 > 3 ?
1 > 7 ?
```

Because they depend on comparisons, their best possible complexity is generally:

```text
O(N log N)
```

Counting Sort is different.

It does **not compare elements**.

Instead it asks:

```text
How many 1s are there?
How many 2s are there?
How many 3s are there?
```

and rebuilds the sorted array using those counts.

---

# Basic Idea

Input:

```java
[1,3,2,4,1]
```

Count frequencies:

```text
1 -> 2 times
2 -> 1 time
3 -> 1 time
4 -> 1 time
```

Store them in an array:

```java
freq[1] = 2;
freq[2] = 1;
freq[3] = 1;
freq[4] = 1;
```

Visual:

```text
index : 0 1 2 3 4

freq  : 0 2 1 1 1
```

---

# Rebuilding The Sorted Array

Since:

```text
1 appears 2 times
```

put:

```text
1 1
```

Since:

```text
2 appears 1 time
```

put:

```text
2
```

Since:

```text
3 appears 1 time
```

put:

```text
3
```

Since:

```text
4 appears 1 time
```

put:

```text
4
```

Result:

```java
[1,1,2,3,4]
```

---

# Basic Counting Sort Code

```java
public static void countingSort(int[] arr) {

    int max = 0;

    for(int num : arr) {
        max = Math.max(max, num);
    }

    int[] freq = new int[max + 1];

    for(int num : arr) {
        freq[num]++;
    }

    int idx = 0;

    for(int i = 0; i <= max; i++) {

        while(freq[i] > 0) {
            arr[idx++] = i;
            freq[i]--;
        }
    }
}
```

---

# Time Complexity

Finding maximum:

```text
O(N)
```

Building frequency array:

```text
O(N)
```

Rebuilding sorted array:

```text
O(K)
```

where:

```text
K = Maximum Value
```

Total:

```text
O(N + K)
```

---

# Space Complexity

Frequency array:

```text
O(K)
```

---

# Advantages

### Faster Than O(N log N)

Suppose:

```text
N = 100000
max value = 100000
```

Then:

```text
O(N + K)
=
O(200000)
```

which is very fast.

---

### No Comparisons

Counting Sort never compares elements.

It simply counts occurrences.

---

### Excellent For Small Integer Ranges

Examples:

```text
Marks: 0 - 100
Ratings: 1 - 5
Ages: 1 - 120
```

---

# Disadvantages

### Works Only For Integer-like Values

Counting Sort relies on array indices.

Example:

```java
freq[value]
```

Not suitable for:

```text
Strings
Objects
Large arbitrary values
```

---

### Memory Depends On Maximum Value

Input:

```java
[1, 1000000000]
```

Need:

```java
int[] freq = new int[1000000001];
```

which is impossible.

---

# When Should I Think About Counting Sort?

Whenever constraints look like:

```text
N <= 100000

value <= 100000
```

Immediately ask:

```text
Can I use a frequency array?
```

Because:

```java
int[] freq = new int[100001];
```

is reasonable.

---

# What Is A Stable Sorting Algorithm?

A sorting algorithm is called Stable if:

```text
Equal elements maintain their original relative order.
```

Example:

Input:

```text
(2,A)
(1,B)
(2,C)
```

Sort by the first value.

Output:

```text
(1,B)
(2,A)
(2,C)
```

Notice:

```text
A was before C originally.
A is still before C.
```

Therefore the sorting algorithm is Stable.

---

# Why Does Stability Matter?

Consider:

```text
(80,Ram)
(60,Sai)
(80,Ravi)
```

After sorting by marks:

```text
(60,Sai)
(80,Ram)
(80,Ravi)
```

Ram should remain before Ravi.

Stable sorting guarantees this.

---

# Is Basic Counting Sort Stable?

No.

The basic frequency version only remembers:

```text
80 occurs twice
```

It does not remember:

```text
Ram
Ravi
```

Therefore the basic implementation is not stable.

---

# Stable Counting Sort

Stable Counting Sort uses:

1. Frequency Array
2. Prefix Sum Array
3. Output Array

---

# What Is Prefix Sum?

Suppose frequencies are:

```text
Value   Frequency

1       1
2       2
3       1
```

Frequency array:

```text
1 2 1
```

Convert it into Prefix Sum:

```text
1 3 4
```

Calculation:

```text
prefix[1] = 1

prefix[2] = 1 + 2 = 3

prefix[3] = 3 + 1 = 4
```

---

# Meaning Of Prefix Sum

```text
prefix[1] = 1
```

means:

```text
There is 1 element <= 1
```

---

```text
prefix[2] = 3
```

means:

```text
There are 3 elements <= 2
```

---

```text
prefix[3] = 4
```

means:

```text
There are 4 elements <= 3
```

---

# Stable Counting Sort Example

Input:

```text
(2,A)
(1,B)
(2,C)
```

Frequency:

```text
1 -> 1
2 -> 2
```

Prefix Sum:

```text
1 -> 1
2 -> 3
```

Process from Right To Left.

---

Current:

```text
(2,C)
```

Position:

```text
prefix[2] - 1 = 2
```

Place:

```text
output[2] = (2,C)
```

Decrease:

```text
prefix[2]--
```

Now:

```text
prefix[2] = 2
```

---

Current:

```text
(1,B)
```

Position:

```text
prefix[1] - 1 = 0
```

Place:

```text
output[0] = (1,B)
```

---

Current:

```text
(2,A)
```

Position:

```text
prefix[2] - 1 = 1
```

Place:

```text
output[1] = (2,A)
```

---

Final Output:

```text
(1,B)
(2,A)
(2,C)
```

Order preserved.

Therefore:

```text
Stable
```

---

# Stable Counting Sort Code

```java
public static int[] countingSortStable(int[] arr) {

    int max = Arrays.stream(arr).max().getAsInt();

    int[] freq = new int[max + 1];

    for(int num : arr) {
        freq[num]++;
    }

    for(int i = 1; i <= max; i++) {
        freq[i] += freq[i - 1];
    }

    int[] output = new int[arr.length];

    for(int i = arr.length - 1; i >= 0; i--) {

        int num = arr[i];

        output[freq[num] - 1] = num;

        freq[num]--;
    }

    return output;
}
```

---

# What Are Bucket Problems?

Bucket Problems group elements into buckets based on value.

Example:

```text
Ratings:
1 to 5
```

Create:

```text
Bucket 1
Bucket 2
Bucket 3
Bucket 4
Bucket 5
```

Count how many ratings belong to each bucket.

Example:

```java
[5,1,5,2,3,5]
```

Buckets:

```text
1 -> 1
2 -> 1
3 -> 1
4 -> 0
5 -> 3
```

This idea is extremely similar to Counting Sort.

---

# Top K Frequent Elements

Input:

```java
[1,1,1,2,2,3]
```

Frequency:

```text
1 -> 3
2 -> 2
3 -> 1
```

Many solutions use frequency buckets:

```text
Bucket 1 = [3]
Bucket 2 = [2]
Bucket 3 = [1]
```

This bucketing idea comes from Counting Sort concepts.

---

# Counting Sort In Radix Sort

Radix Sort sorts one digit at a time.

Example:

```text
170
045
075
090
802
024
002
066
```

It repeatedly performs Counting Sort on:

1. Units digit
2. Tens digit
3. Hundreds digit

The Counting Sort used here must be Stable.

Otherwise previous digit ordering gets destroyed and Radix Sort fails.

---
