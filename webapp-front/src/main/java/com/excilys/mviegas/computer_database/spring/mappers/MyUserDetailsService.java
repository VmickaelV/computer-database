package com.excilys.mviegas.computer_database.spring.mappers;

import com.excilys.mviegas.computer_database.data.User;
import com.excilys.mviegas.computer_database.persistence.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

/**
 * "Dao" de UserDetails pour SpringSecurity
 *
 * @author VIEGAS Mickael
 */
@Component
public class MyUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	/**
	 * Cache d'utilisateurs
	 */
	private Map<String, UserDetails> mCache = new TreeMap<>();

	@Autowired
	private IUserDao mUserDao;

	@Override
	public UserDetails loadUserByUsername(String pName) throws UsernameNotFoundException {
		if (mCache.containsKey(pName)) {
			return mCache.get(pName);
		} else {
			User user = mUserDao.findByName(pName);
			if (user == null) {
				throw new UsernameNotFoundException("\"" + pName + "\" user not found.");
			}

			UserDetails userDetails = new UserDetailMapper.MyUserDetails(user);
			mCache.put(pName, userDetails);
			return userDetails;
		}
	}

	/**
	 * Invalidation du Cache
	 */
	public void invalidate() {
		mCache.clear();
	}
}
