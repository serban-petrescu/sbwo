package spet.sbwo.control.controller.bo;

import spet.sbwo.control.channel.base.JournalChannel;

public interface IBoController<C extends JournalChannel> {

    int create(C data, String username);

    C read(int id);

    void update(int id, C data, String username);

    void delete(int id, String username);

    void restore(int id, String username);

}
