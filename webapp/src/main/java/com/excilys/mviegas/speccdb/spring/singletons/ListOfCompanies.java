package com.excilys.mviegas.speccdb.spring.singletons;

import com.excilys.mviegas.speccdb.C;
import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.ICompanyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Bean Constant et Singleton de liste des Companies
 *
 * Created by excilys on 15/04/16.
 */
@Component
public class ListOfCompanies implements List<Company> {

	@Autowired
	private ICompanyDao mCompanyDao;

	private List<Company> mCompanies;

	@PostConstruct
	public void init() {
		try {
			mCompanies = mCompanyDao.findAll(0, 0);
		} catch (DAOException pE) {
			C.Loggers.RUNTIME.error(pE.getMessage(), pE);
			throw new RuntimeException("Error when retrieving the list of companies");
		}
	}

	@Override
	public int size() {
		return mCompanies.size();
	}

	@Override
	public boolean isEmpty() {
		return mCompanies.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return mCompanies.contains(o);
	}

	@Override
	public Iterator<Company> iterator() {
		return mCompanies.iterator();
	}

	@Override
	public Object[] toArray() {
		return mCompanies.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return mCompanies.toArray(a);
	}

	@Override
	public boolean add(Company pCompany) {
		return mCompanies.add(pCompany);
	}

	@Override
	public boolean remove(Object o) {
		return mCompanies.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return mCompanies.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Company> c) {
		return mCompanies.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Company> c) {
		return mCompanies.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return mCompanies.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return mCompanies.retainAll(c);
	}

	@Override
	public void replaceAll(UnaryOperator<Company> operator) {
		mCompanies.replaceAll(operator);
	}

	@Override
	public void sort(Comparator<? super Company> c) {
		mCompanies.sort(c);
	}

	@Override
	public void clear() {
		mCompanies.clear();
	}

	@Override
	public boolean equals(Object o) {
		return mCompanies.equals(o);
	}

	@Override
	public int hashCode() {
		return mCompanies.hashCode();
	}

	@Override
	public Company get(int index) {
		return mCompanies.get(index);
	}

	@Override
	public Company set(int index, Company element) {
		return mCompanies.set(index, element);
	}

	@Override
	public void add(int index, Company element) {
		mCompanies.add(index, element);
	}

	@Override
	public Company remove(int index) {
		return mCompanies.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return mCompanies.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return mCompanies.lastIndexOf(o);
	}

	@Override
	public ListIterator<Company> listIterator() {
		return mCompanies.listIterator();
	}

	@Override
	public ListIterator<Company> listIterator(int index) {
		return mCompanies.listIterator(index);
	}

	@Override
	public List<Company> subList(int fromIndex, int toIndex) {
		return mCompanies.subList(fromIndex, toIndex);
	}

	@Override
	public Spliterator<Company> spliterator() {
		return mCompanies.spliterator();
	}

	@Override
	public void forEach(Consumer<? super Company> action) {
		mCompanies.forEach(action);
	}

	@Override
	public boolean removeIf(Predicate<? super Company> filter) {
		return mCompanies.removeIf(filter);
	}

	@Override
	public Stream<Company> stream() {
		return mCompanies.stream();
	}

	@Override
	public Stream<Company> parallelStream() {
		return mCompanies.parallelStream();
	}
}
