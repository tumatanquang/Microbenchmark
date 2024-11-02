package uc.util;
import java.util.ArrayList;
import edu.emory.mathcs.backport.java.util.concurrent.locks.ReentrantLock;
public class ConcurrentArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = -6881913451159539758L;
	public ConcurrentArrayList() {
		super();
	}
	public ConcurrentArrayList(int initialCapacity) {
		super(initialCapacity);
	}
	public final ConcurrentArrayList<E> shared() {
		return new Shared(this);
	}
	private final class Shared extends ConcurrentArrayList<E> {
		private static final long serialVersionUID = 5903643726292984434L;
		private final ConcurrentArrayList<E> list;
		private final ReentrantLock rwLock;
		private Shared(ConcurrentArrayList<E> target) {
			list = target;
			rwLock = new ReentrantLock();
		}
		@Override
		public boolean add(E value) {
			final ReentrantLock lock = rwLock;
			lock.lock();
			try {
				return list.add(value);
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public void clear() {
			final ReentrantLock lock = rwLock;
			lock.lock();
			try {
				list.clear();
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public boolean contains(Object o) {
			final ReentrantLock lock = rwLock;
			lock.lock();
			try {
				return list.contains(o);
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public E get(int index) {
			final ReentrantLock lock = rwLock;
			lock.lock();
			try {
				return list.get(index);
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public int indexOf(Object o) {
			final ReentrantLock lock = rwLock;
			lock.lock();
			try {
				return list.indexOf(o);
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public boolean isEmpty() {
			final ReentrantLock lock = rwLock;
			lock.lock();
			try {
				return list.isEmpty();
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public E remove(int index) {
			final ReentrantLock lock = rwLock;
			lock.lock();
			try {
				return list.remove(index);
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public boolean remove(Object o) {
			final ReentrantLock lock = rwLock;
			lock.lock();
			try {
				return list.remove(o);
			}
			finally {
				lock.unlock();
			}
		}
		@Override
		public int size() {
			final ReentrantLock lock = rwLock;
			lock.lock();
			try {
				return list.size();
			}
			finally {
				lock.unlock();
			}
		}
	}
}