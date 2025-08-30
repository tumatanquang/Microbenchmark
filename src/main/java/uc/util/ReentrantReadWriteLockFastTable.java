package uc.util;
import javolution.util.FastTable;
import javolution.util.internal.ReadWriteLockImpl;
public class ReentrantReadWriteLockFastTable<E> extends FastTable<E> {
	private static final long serialVersionUID = 2781771606854788734L;
	public ReentrantReadWriteLockFastTable() {
		super();
	}
	public ReentrantReadWriteLockFastTable(int capacity) {
		super(capacity);
	}
	@Override
	public final ReentrantReadWriteLockFastTable<E> shared() {
		return new Shared<E>(this);
	}
	private static final class Shared<E> extends ReentrantReadWriteLockFastTable<E> {
		private static final long serialVersionUID = 29253114956268253L;
		private final ReentrantReadWriteLockFastTable<E> table;
		private final ReadWriteLockImpl rwLock;
		private Shared(ReentrantReadWriteLockFastTable<E> target) {
			table = target;
			rwLock = new ReadWriteLockImpl();
		}
		@Override
		public boolean add(E value) {
			final ReadWriteLockImpl lock = rwLock;
			lock.writeLock.lock();
			try {
				return table.add(value);
			}
			finally {
				lock.writeLock.unlock();
			}
		}
		@Override
		public void clear() {
			final ReadWriteLockImpl lock = rwLock;
			lock.writeLock.lock();
			try {
				table.clear();
			}
			finally {
				lock.writeLock.unlock();
			}
		}
		@Override
		public boolean contains(Object o) {
			final ReadWriteLockImpl lock = rwLock;
			lock.readLock.lock();
			try {
				return table.contains(o);
			}
			finally {
				lock.readLock.unlock();
			}
		}
		@Override
		public E get(int index) {
			final ReadWriteLockImpl lock = rwLock;
			lock.readLock.lock();
			try {
				return table.get(index);
			}
			finally {
				lock.readLock.unlock();
			}
		}
		@Override
		public int indexOf(Object o) {
			final ReadWriteLockImpl lock = rwLock;
			lock.readLock.lock();
			try {
				return table.indexOf(o);
			}
			finally {
				lock.readLock.unlock();
			}
		}
		@Override
		public boolean isEmpty() {
			final ReadWriteLockImpl lock = rwLock;
			lock.readLock.lock();
			try {
				return table.isEmpty();
			}
			finally {
				lock.readLock.unlock();
			}
		}
		@Override
		public E remove(int index) {
			final ReadWriteLockImpl lock = rwLock;
			lock.writeLock.lock();
			try {
				return table.remove(index);
			}
			finally {
				lock.writeLock.unlock();
			}
		}
		@Override
		public boolean remove(Object o) {
			final ReadWriteLockImpl lock = rwLock;
			lock.writeLock.lock();
			try {
				return table.remove(o);
			}
			finally {
				lock.writeLock.unlock();
			}
		}
		@Override
		public int size() {
			final ReadWriteLockImpl lock = rwLock;
			lock.readLock.lock();
			try {
				return table.size();
			}
			finally {
				lock.readLock.unlock();
			}
		}
	}
}