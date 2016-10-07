package com.airamerica;

import java.util.Comparator;
import java.util.Iterator;

public class SortedList <T> implements Iterable<T> {

	private SortedListNode<T> head;
	private int size;
	private Comparator<T> c;

	public SortedList(Comparator<T> c){
		super();
		this.head = null;
		this.size = 0;
		this.c = c;
	}


	private class SortedListIterator implements Iterator<T>{
		private SortedListNode<T> curr=head;
		private boolean checkedFirst;
		public SortedListIterator(){
			this.curr=head;
			checkedFirst=false;
		}
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			if(curr != null){
				return curr.getNext() != null;
			}else{
				return false;
			}
		}

		@Override
		public T next() {
			// TODO Auto-generated method stub
			if(!checkedFirst){
				return curr.getData();
			}
			curr = curr.getNext();
			return curr.getData();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}


	}


	public boolean add(T element) {
		// TODO Auto-generated method stub
		SortedListNode<T> e = new SortedListNode<T>(null, element);

		if(this.head == null){
			this.head = e;
			this.size++;
			return true;
		}else{
			if(this.c.compare(e.getData(), head.getData()) < 0){
				e.setNext(head);
				this.head = e;
				this.size++;
				return true;
			}else{
				SortedListNode<T> curr = head;
				while(curr.getNext() != null){
					if(c.compare(e.getData(), curr.getNext().getData()) > 0){
						e.setNext(curr.getNext());
						curr.setNext(e);
						this.size++;
						return true;
					}
					curr = curr.getNext();
				}
				curr.setNext(e);
				this.size++;
				return true;
			}
		}
	}


	public void clear() {
		this.head = null;
		this.size = 0;

	}


	public boolean contains(Object o) {
		SortedListNode<T> curr = head;
		while(curr != null){
			if(o.equals(curr.getData())){
				return true;
			}
			curr = curr.getNext();
		}
		return false;
	}




	public T get(int index) {
		SortedListNode<T> curr = head;
		for(int i=0; i<index; i++){
			curr = curr.getNext();
		}
		return curr.getData();
	}


	public int indexOf(Object o) {
		int i=0;
		SortedListNode<T> curr = head;
		while(curr != null){
			if(curr.getData().equals(o)){
				return i;
			}
			i++;
		}
		return -1;
	}


	public boolean isEmpty() {
		return this.head == null;
	}


	@Override
	public Iterator<T> iterator() {
		return new SortedListIterator();
	}


	public SortedListNode<T> remove(int index) {
		SortedListNode<T> curr = head;
		if(index == 0){
			this.head = head.getNext();
			this.size--;
			return this.head;
		}else{
			for(int i=0; i<index-1; i++){
				curr = curr.getNext();
			}
			SortedListNode<T> temp = curr;

			curr.setNext(curr.getNext().getNext());
			this.size--;
			return temp;
		}
	}



	public int size() {
		return this.size;
	}



}

