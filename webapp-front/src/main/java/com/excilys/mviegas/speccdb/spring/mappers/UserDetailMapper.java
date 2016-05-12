package com.excilys.mviegas.speccdb.spring.mappers;

import com.excilys.mviegas.speccdb.data.Authorization;
import com.excilys.mviegas.speccdb.data.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Map de {@link User} vers {@link UserDetails}
 *
 * @author VIEGAS Mickael
 */
public class UserDetailMapper {

	//=============================================================
	// Methods static
	//=============================================================
	public MyGrantedAuthority make(Authorization pAuthorization) {
		return new MyGrantedAuthority(pAuthorization);
	}

	public MyUserDetails make(User pUser) {
		return new MyUserDetails(pUser);
	}

	//=============================================================
	// Inner Class
	//=============================================================
	public static class MyGrantedAuthority implements GrantedAuthority {
		private Authorization mAuthorization;

		public MyGrantedAuthority(Authorization pAuthorization) {
			mAuthorization = pAuthorization;
		}

		@Override
		public String getAuthority() {
			return mAuthorization.getName();
		}
	}

	public static class MyUserDetails implements UserDetails {

		private String mUsername;
		private String mPassword;
		private boolean mEnabled;
		private List<MyGrantedAuthority> mMyGrantedAuthorityList;

		public MyUserDetails(User pUser) {
			mUsername = pUser.getUsername();
			mPassword = pUser.getPassword();
			mEnabled = pUser.isEnabled();
			mMyGrantedAuthorityList = new ArrayList<>(20);
			pUser.getAuthorizationList().forEach(a -> mMyGrantedAuthorityList.add(new MyGrantedAuthority(a)));
			pUser.getGroupList().forEach(g -> g.getAuthorizationList().forEach(a -> mMyGrantedAuthorityList.add(new MyGrantedAuthority(a))));
		}

		@Override
		public boolean isEnabled() {
			return mEnabled;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public String getUsername() {
			return mUsername;
		}

		@Override
		public String getPassword() {
			return mPassword;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return mMyGrantedAuthorityList;
		}
	}
}
