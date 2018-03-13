package com.epam.pharmacy.connection;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import com.epam.pharmacy.exceptions.ConnectionException;

public class ResourcesQueue<T> {

	private static final int TIME_OUT = 5000;
	private Semaphore semaphore;
	private Queue<T> queue = new ConcurrentLinkedQueue<T>();

	public ResourcesQueue(Queue<T> queue, int size) {
		this.semaphore = new Semaphore(size, true);
		this.queue.addAll(queue);
	}

	public ResourcesQueue(int size) {
		this.semaphore = new Semaphore(size, true);
	}

	public T takeResource() throws ConnectionException {
		try {
			if (semaphore.tryAcquire(TIME_OUT, TimeUnit.MILLISECONDS)) {
				T resource = queue.poll();
				return resource;
			}
		} catch (InterruptedException e) {
			throw new ConnectionException(e);
		}
		throw new ConnectionException("You didn't wait for connect bro.");
	}

	public void returnResource(T resource) {
		queue.add(resource);
		semaphore.release();
	}

	public void addResource(T resource) {
		queue.add(resource);
	}

	public int size() {
		return queue.size();
	}
}
