package com.excilys.mviegas.speccdb.spring.mappers;

import com.excilys.mviegas.speccdb.data.User;
import com.excilys.mviegas.speccdb.persistence.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author VIEGAS Mickael
 */
@Component
public class MyUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private IUserDao mUserDao;

	@Override
	public UserDetails loadUserByUsername(String pName) throws UsernameNotFoundException {
		User user;
		user = mUserDao.findByName(pName);
		if (user == null) {
			throw new UsernameNotFoundException("\""+pName+"\" user not found.");
		}

		return new UserDetailMapper.MyUserDetails(user);
	}
}
