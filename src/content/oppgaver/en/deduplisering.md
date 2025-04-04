---
title: deduplication
---

## Deduplication

### Background

This task has the goal of making you a bit more familiar with a important concept in distributed event-driven systems, being duplicated events. It is rare that a distributed system can guarantee "once-and-only-once-delivery" of messages. You have to choose between "at-most-once-delivery" or "at-least-once-delivery", where the later is chosen. It is therefore important to build systems that can handle the same message being delivered multiple times.

Interesting article on the problem with [exactly-once-delivery](https://www.confluent.io/blog/exactly-once-semantics-are-possible-heres-how-apache-kafka-does-it/)

### Task

