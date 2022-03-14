package org.arathok.wurmunlimited.mods.renamePets;

import com.wurmonline.server.Items;
import com.wurmonline.server.Players;
import com.wurmonline.server.Servers;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.Creatures;
import com.wurmonline.server.creatures.DbCreatureStatus;
import com.wurmonline.server.creatures.NoSuchCreatureException;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.villages.Village;
import com.wurmonline.server.zones.VirtualZone;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

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
            return performer.isPlayer() && target.getBrandVillage() == performer.getCitizenVillage() && (performer.getPet()==target || target.isCaredFor(Players.getInstance().getPlayerOrNull(performer.getWurmId())));
        }


        @Override
        public boolean action(Action action, Creature performer,Item source,  Creature target, short num, float counter)
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
                if(source.getInscription()!=null)
                    if (!source.getInscription().getInscription().isEmpty()) {
                        performer.getCommunicator().sendSafeServerMessage("you rename the "+target.getName()+" to "+source.getInscription().getInscription());
                        RenamePets.logger.log(Level.INFO,performer.getName()+" renamed" +target.getName() +" to "+source.getInscription().getInscription() );
                        String newName = source.getInscription().getInscription();
                        try {
                            Creatures.getInstance().getCreature(target.getWurmId()).setName(newName);
                        } catch (NoSuchCreatureException e) {
                            RenamePets.logger.log(Level.INFO, "no such creature!");
                            e.printStackTrace();
                        }

                        try {
                            ReflectionUtil.callPrivateMethod(target.getStatus(), ReflectionUtil.getMethod(DbCreatureStatus.class, "saveCreatureName", new Class[]{String.class}), newName);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }

                        target.refreshVisible();
                        Items.destroyItem(source.getWurmId());

                    }
            return propagate(action,
                    ActionPropagation.FINISH_ACTION,
                    ActionPropagation.NO_SERVER_PROPAGATION,
                    ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
        }

    }


