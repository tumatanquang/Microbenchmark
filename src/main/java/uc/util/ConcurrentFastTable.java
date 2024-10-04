package uc.util;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javolution.util.FastTable;
public class ConcurrentFastTable<E> extends FastTable<E> {
	private static final long serialVersionUID = 2781771606854788734L;
	public ConcurrentFastTable() {
		super();
	}
	public ConcurrentFastTable(int capacity) {
		super(capacity);
	}
	public boolean addElement(E value) {
		return super.add(value);
	}
	public void clearAll() {
		super.clear();
	}
	public boolean contain(Object o) {
		return super.contains(o);
	}
	public E getElement(int index) {
		return super.get(index);
	}
	public int getIndexOf(Object o) {
		return super.indexOf(o);
	}
	public boolean empty() {
		return super.isEmpty();
	}
	public E removeElement(int index) {
		return super.remove(index);
	}
	public int getSize() {
		return super.size();
	}
	@Override
	public final ConcurrentFastTable<E> shared() {
		return new Shared();
	}
	private final class Shared extends ConcurrentFastTable<E> {
		private static final long serialVersionUID = 29253114956268253L;
		private final ReadWriteLock lock;
		private Shared() {
			lock = new ReentrantReadWriteLock();
		}
		@Override
		public boolean addElement(E value) {
			lock.writeLock().lock();
			try {
				return super.add(value);
			}
			finally {
				lock.writeLock().unlock();
			}
		}
		@Override
		public void clearAll() {
			lock.writeLock().lock();
			try {
				super.clear();
			}
			finally {
				lock.writeLock().unlock();
			}
		}
		@Override
		public boolean contain(Object o) {
			lock.readLock().lock();
			try {
				return super.contains(o);
			}
			finally {
				lock.readLock().unlock();
			}
		}
		@Override
		public E getElement(int index) {
			lock.readLock().lock();
			try {
				return super.get(index);
			}
			finally {
				lock.readLock().unlock();
			}
		}
		@Override
		public int getIndexOf(Object o) {
			lock.readLock().lock();
			try {
				return super.indexOf(o);
			}
			finally {
				lock.readLock().unlock();
			}
		}
		@Override
		public boolean empty() {
			lock.readLock().lock();
			try {
				return super.isEmpty();
			}
			finally {
				lock.readLock().unlock();
			}
		}
		@Override
		public E removeElement(int index) {
			lock.writeLock().lock();
			try {
				return super.remove(index);
			}
			finally {
				lock.writeLock().unlock();
			}
		}
		@Override
		public boolean remove(Object o) {
			lock.writeLock().lock();
			try {
				return super.remove(o);
			}
			finally {
				lock.writeLock().unlock();
			}
		}
		@Override
		public int getSize() {
			lock.readLock().lock();
			try {
				return super.size();
			}
			finally {
				lock.readLock().unlock();
			}
		}
	}
}