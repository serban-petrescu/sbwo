package spet.sbwo.data.access;

import javax.persistence.EntityManager;

@FunctionalInterface
interface IUpdateCommand {

	void run(EntityManager em);

}
