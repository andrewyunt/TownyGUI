package com.andrewyunt.townygui.utilities;

import com.andrewyunt.townygui.TownyGUI;
import com.palmergames.bukkit.towny.TownyMessaging;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandBuilder implements ConversationAbandonedListener {
	
	private final List<String> argumentList;
	
	private final ConversationFactory conversationFactory;

	private int conversationLength;
	
	private String newCommand;
	private final String baseCommand;
	
	public CommandBuilder(Set<String> argumentSet, String inputCommand) {
		
		newCommand = inputCommand;
		baseCommand = inputCommand;
		
		argumentList = new ArrayList<>(argumentSet);
		
		conversationLength = argumentList.size();
		
        conversationFactory = new ConversationFactory(TownyGUI.plugin)
                .withModality(true)
                .withFirstPrompt(new CommandPrompt())
                .withEscapeSequence("quit")
                .withTimeout(30)
                .thatExcludesNonPlayersWithMessage("Unable to access from the console.")
                .addConversationAbandonedListener(this);
	}
	
	public void beginConversation(CommandSender sender) {
		
		conversationFactory.buildConversation((Conversable) sender).begin();
	}
    
    private class CommandPrompt extends StringPrompt {
    	
        public String getPromptText(ConversationContext context) {
        	
        	String prompt = TownyGUI.plugin.commandConfig.getConfig().getString("commands." + baseCommand + ".arguments." + argumentList.get(conversationLength - 1));
        	
        	return ChatColor.translateAlternateColorCodes('&', prompt);
        }

		@Override
		public Prompt acceptInput(ConversationContext context, String input) {
			
			conversationLength = conversationLength - 1;
			
			newCommand = newCommand + " " + input;
			
			if(conversationLength > 0)
				return new CommandPrompt();
			
			return Prompt.END_OF_CONVERSATION;
		}
    }

	@Override
	public void conversationAbandoned(ConversationAbandonedEvent event) {
    
		Conversable who = event.getContext().getForWhom();
		
		if(!event.gracefulExit())
			TownyMessaging.sendErrorMsg((CommandSender) who, "Command execution stopped by " + event.getCanceller().getClass().getName());
		else {
			newCommand = newCommand.replace("/", "");
			
			Bukkit.getServer().dispatchCommand((CommandSender) who, newCommand); 
		}
	}
}