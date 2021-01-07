package main

import "fmt"

type LinkedListQueue struct {
	first  *Node
	last   *Node
	length int
}

type Node struct {
	item int
	next *Node
}

func (queue *LinkedListQueue) isEmpty() bool {
	return queue.length == 0
}

func (queue *LinkedListQueue) size() int {
	return queue.length
}

func (queue *LinkedListQueue) enqueue(item int) {
	oldLast := queue.last
	queue.last = &Node{item, nil}
	if queue.isEmpty() {
		queue.first = queue.last
	} else {
		oldLast.next = queue.last;
	}
	queue.length++
}

func (queue *LinkedListQueue) dequeue() int {
	item := queue.first.item
	queue.first = queue.first.next
	if queue.isEmpty() {
		queue.last = nil
	}
	queue.length--
	return item
}

func main() {
	queue :=  LinkedListQueue{nil,nil,0}
	queue.enqueue(1)
	queue.enqueue(2)
	queue.enqueue(3)
	queue.enqueue(4)
	fmt.Println(queue.size())
	fmt.Println(queue.dequeue())
	fmt.Println(queue.dequeue())
	fmt.Println(queue.size())
}
