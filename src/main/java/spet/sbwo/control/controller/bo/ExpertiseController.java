package spet.sbwo.control.controller.bo;

import java.time.Duration;

import spet.sbwo.config.ControlEntry;
import spet.sbwo.control.action.bo.base.CreateEntity;
import spet.sbwo.control.action.bo.base.DeleteEntity;
import spet.sbwo.control.action.bo.base.ReadEntity;
import spet.sbwo.control.action.bo.base.RestoreEntity;
import spet.sbwo.control.action.bo.base.UpdateEntity;
import spet.sbwo.control.action.bo.expertise.CreateExpertise;
import spet.sbwo.control.action.bo.expertise.DeleteExpertise;
import spet.sbwo.control.action.bo.expertise.ReadExpertise;
import spet.sbwo.control.action.bo.expertise.RestoreExpertise;
import spet.sbwo.control.action.bo.expertise.UpdateExpertise;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.table.Expertise;

public class ExpertiseController extends BaseBoController<Expertise, ExpertiseChannel> {

    public ExpertiseController(IDatabaseExecutorCreator database, ControlEntry config) {
        super(database, config);
    }

    @Override
    protected CreateEntity<Expertise, ExpertiseChannel> createAction() {
        return new CreateExpertise();
    }

    @Override
    protected ReadEntity<Expertise, ExpertiseChannel> readAction() {
        return new ReadExpertise();
    }

    @Override
    protected UpdateEntity<Expertise, ExpertiseChannel> updateAction() {
        return new UpdateExpertise();
    }

    @Override
    protected DeleteEntity<Expertise> deleteAction(Duration directDeleteInterval) {
        return new DeleteExpertise(directDeleteInterval);
    }

    @Override
    protected RestoreEntity<Expertise> restoreAction() {
        return new RestoreExpertise();
    }

}
