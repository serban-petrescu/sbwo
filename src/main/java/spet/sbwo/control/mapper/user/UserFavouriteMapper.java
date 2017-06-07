package spet.sbwo.control.mapper.user;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import spet.sbwo.control.channel.user.UserFavouriteChannel;
import spet.sbwo.control.mapper.IMapper;
import spet.sbwo.data.table.UserFavourite;

import static spet.sbwo.control.mapper.Utils.NOT_NULL;

public class UserFavouriteMapper implements IMapper<UserFavourite, UserFavouriteChannel> {
    private static final ModelMapper mapper = new ModelMapper();

    static {
        mapper.addMappings(new PropertyMap<UserFavouriteChannel, UserFavourite>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                when(NOT_NULL).map(source.getHash(), destination.getHash());
                when(NOT_NULL).map(source.getTitle(), destination.getTitle());
            }
        });
    }

    private UserFavouriteMapper() {
        super();
    }

    public static IMapper<UserFavourite, UserFavouriteChannel> newInstance() {
        return new UserFavouriteMapper();
    }

    @Override
    public UserFavouriteChannel toChannel(UserFavourite entity) {
        return mapper.map(entity, UserFavouriteChannel.class);
    }

    @Override
    public UserFavourite toEntity(UserFavouriteChannel channel) {
        return mapper.map(channel, UserFavourite.class);
    }

    @Override
    public void mergeIntoEntity(UserFavouriteChannel channel, UserFavourite entity) {
        mapper.map(channel, entity);
    }
}
