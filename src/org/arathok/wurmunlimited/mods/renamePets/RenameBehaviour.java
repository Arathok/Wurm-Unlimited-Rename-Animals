package org.arathok.wurmunlimited.mods.renamePets;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemList;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RenameBehaviour implements BehaviourProvider {



    private final List<ActionEntry> rename;
    private final RenameAction renameAction;

    public RenameBehaviour() {
        this.renameAction = new RenameAction();
        this.rename = Collections.singletonList(renameAction.actionEntry);
        ModActions.registerActionPerformer(renameAction);

    }

    //, , , , ,
    //, , , , ;

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Creature target) {

        if (RenameAction.canUse(performer,target)) {
            if(source.getTemplateId()==ItemList.paperSheet)
                return new ArrayList<>(rename);

        } else
            return null;


        return null;
    }
}