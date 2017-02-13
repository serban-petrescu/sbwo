package spet.sbwo.control.controller.bo;

import spet.sbwo.control.action.bo.base.CreateEntityAction;
import spet.sbwo.control.action.bo.base.DeleteEntityAction;
import spet.sbwo.control.action.bo.base.ReadEntityAction;
import spet.sbwo.control.action.bo.base.RestoreEntityAction;
import spet.sbwo.control.action.bo.base.UpdateEntityAction;
import spet.sbwo.control.action.bo.expertise.CreateExpertiseAction;
import spet.sbwo.control.action.bo.expertise.DeleteExpertiseAction;
import spet.sbwo.control.action.bo.expertise.ReadExpertiseAction;
import spet.sbwo.control.action.bo.expertise.RestoreExpertiseAction;
import spet.sbwo.control.action.bo.expertise.UpdateExpertiseAction;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.table.Expertise;

public class ExpertiseController extends BaseBoController<Expertise, ExpertiseChannel> {

	public ExpertiseController(IDatabaseExecutorCreator database, int directDeleteInterval) {
		super(database, directDeleteInterval);
	}

	@Override
	protected CreateEntityAction<Expertise, ExpertiseChannel> createAction() {
		return new CreateExpertiseAction();
	}

	@Override
	protected ReadEntityAction<Expertise, ExpertiseChannel> readAction() {
		return new ReadExpertiseAction();
	}

	@Override
	protected UpdateEntityAction<Expertise, ExpertiseChannel> updateAction() {
		return new UpdateExpertiseAction();
	}

	@Override
	protected DeleteEntityAction<Expertise> deleteAction(int directDeleteInterval) {
		return new DeleteExpertiseAction(directDeleteInterval);
	}

	@Override
	protected RestoreEntityAction<Expertise> restoreAction() {
		return new RestoreExpertiseAction();
	}

}
