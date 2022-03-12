package org.arathok.wurmunlimited.mods.renamePets;

import com.wurmonline.server.creatures.Communicator;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RenamePets implements WurmServerMod, Initable, PreInitable, Configurable, ItemTemplatesCreatedListener, ServerStartedListener, ServerPollListener, PlayerMessageListener{

        public static final Logger logger = Logger.getLogger("RenamePets");


        @Override
        public void configure(Properties properties) {


        }


        @Override
        public void preInit() {

            ModActions.init();

        }

        @Override
        public boolean onPlayerMessage(Communicator arg0, String arg1) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void onItemTemplatesCreated() {

            // TODO Auto-generated method stub

        }

        @Override
        public void onServerStarted() {
            // TODO Auto-generated method stub
                logger.log(Level.INFO,"renameAnimals being loaded");
                ModActions.registerBehaviourProvider(new RenameBehaviour());
        }



        @Override
        public void init() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onServerPoll() {



        }





    }


