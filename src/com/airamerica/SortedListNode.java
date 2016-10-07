package com.airamerica;

public class SortedListNode <T>{
	private SortedListNode<T> next;
	private T data;
	public SortedListNode(SortedListNode<T> next, T data) {
		super();
		this.next = next;
		this.data = data;
	}
	public SortedListNode<T> getNext() {
		return next;
	}
	public void setNext(SortedListNode<T> next) {
		this.next = next;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
	
}
