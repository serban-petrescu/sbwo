package spet.sbwo.control.mapper;

import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.UserFavouriteChannel;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.User;
import spet.sbwo.data.table.UserFavourite;

public class UserFavouriteMapper extends BaseMapper<UserFavourite, UserFavouriteChannel> {
	private User user;

	public UserFavouriteMapper(IDatabaseExecutor executor, User user) {
		super(executor);
		this.user = user;
	}

	@Override
	protected UserFavourite newInternal(UserFavouriteChannel external) throws ControlException {
		return new UserFavourite();
	}

	@Override
	protected UserFavouriteChannel newExternal(UserFavourite internal) throws ControlException {
		return new UserFavouriteChannel();
	}

	@Override
	public void merge(UserFavourite internal, UserFavouriteChannel external) throws ControlException {
		super.merge(internal, external);
		internal.setHash(external.getHash());
		internal.setTitle(external.getTitle());
		internal.setUser(user);
	}

	@Override
	public void merge(UserFavouriteChannel external, UserFavourite internal) throws ControlException {
		super.merge(external, internal);
		external.setHash(internal.getHash());
		external.setTitle(internal.getTitle());
	}

	@Override
	public List<UserFavourite> merge(List<UserFavourite> internal, List<UserFavouriteChannel> external)
			throws ControlException {
		return super.merge(internal, external);
	}
}
