package org.arathok.wurmunlimited.mods.renamePets;

import com.wurmonline.server.Servers;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.villages.Village;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

public class RenameAction implements ActionPerformer
    {

        public ActionEntry actionEntry;


        public RenameAction()
        {
            actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "rename", "renaming",
                                                 new int[]{
                                                         6 /* ACTION_TYPE_NOMOVE */,
                                                         48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                                                         36 /*  SOURCE AND TARGET */,

                                                 }).range(4).build();

            ModActions.registerAction(actionEntry);

        }


        @Override
        public short getActionId()
        {
            return (actionEntry.getNumber());
        }

        public static boolean canUse(Creature performer, Creature target)
        {
            return performer.isPlayer() && target.getBrandVillage() == performer.getCitizenVillage() || performer.getPet()==target;
        }


        @Override
        public boolean action(Action action, Creature performer, Creature target, short num, float counter)
        {

            //Alchemy.logger.log(Level.INFO, "BLAH BLAH HE PERFORMS");


            if (!canUse(performer, target))
            {
                performer.getCommunicator().sendAlertServerMessage("You are not allowed to do that");
                return propagate(action,
                                 ActionPropagation.FINISH_ACTION,
                                 ActionPropagation.NO_SERVER_PROPAGATION,
                                 ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

            }
            else
                if (target.gettam)
        }

    }


