package spet.sbwo.control.controller.bo;

import spet.sbwo.config.ControlEntry;
import spet.sbwo.control.action.bo.base.*;
import spet.sbwo.control.action.bo.expertise.*;
import spet.sbwo.control.channel.expertise.ExpertiseChannel;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.table.Expertise;

import java.time.Duration;

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
