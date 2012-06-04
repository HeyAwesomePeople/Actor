package com.insofar.actor.commands.author;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import com.insofar.actor.ActorPlugin;
import com.insofar.actor.author.EntityActor;

/**
 * InfoCraft Plugin command to save actor recording to a file
 * 
 * @author Joshua Weinberg
 *
 */
public class SaveScene extends AuthorBaseCommand {

	/*********************************************************************
	 * 
	 * BUKKIT COMMAND
	 * 
	 *********************************************************************/

	@Override
	/**
	 * args(dir)
	 * Save all current actors (which the player can view) to directory 'dir'
	 */
	public boolean execute()
	{
		if (args.length != 1)
		{
			player.sendMessage("Error: parameter required: directory");
			return true;
		}

		String dir = FilenameUtils.separatorsToSystem(plugin.scenePath+"/"+args[0]);
		File sceneDir = new File(dir);
		sceneDir.mkdirs();
		
		for (EntityActor ea : plugin.actors)
		{
			if (ea.hasViewer(player))
			{
				String path = FilenameUtils.separatorsToSystem(dir+"/"+ea.name);

				try
				{
					ActorPlugin.instance.saveActorRecording(ea, path);
				}
				catch (IOException e)
				{
					player.sendMessage("Error: Cannot save to "+path);
					player.sendMessage("Command stopped.");
					return false;
				}

			}
		}

		player.sendMessage("Saved all actors.");
		return true;
	}
}