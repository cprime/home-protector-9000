package ai;

import model.Bot;
import model.World;
import model.ProtectorBot;

public class BotAiStateManager {
	private World world;
	private Bot bot;
	
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public Bot getBot() {
		return bot;
	}
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	
	public BotAiStateManager(World world, Bot bot) {
		super();
		
		this.setBot(bot);
		this.setWorld(world);
	}
	
	public void updateBotAiState() {
		AiState previousState = bot.getAiState();
//		boolean fire = this.getWorld().containsFire();
//		
//		if(((ProtectorBot)this.bot).isLowPowered()) {
//			if(previousState == null || !(previousState instanceof SeekRechargeAiState)) {
//				System.out.println("Switching to state: SeekRechargeAiState");
//				bot.setAiState(new SeekRechargeAiState(bot, world));
//			}
//		} else if(fire && !((ProtectorBot)this.bot).hasExtinguisher()) {
//			if(previousState == null || !(previousState instanceof PickupExtinguisherAiState)) {
//				System.out.println("Switching to state: PickupExtinguisherAiState");
//				bot.setAiState(new PickupExtinguisherAiState(bot, world));
//			}
//		} else if(fire && ((ProtectorBot)this.bot).hasExtinguisher()) {
//			if(previousState == null || !(previousState instanceof FiremanAiState)) {
//				System.out.println("Switching to state: FiremanAiState");
//				bot.setAiState(new FiremanAiState(bot, world));
//			}
//		} else if(!fire && ((ProtectorBot)this.bot).hasExtinguisher()) {
//			if(previousState == null || !(previousState instanceof ReplaceExtinguisherAiState)) {
//				System.out.println("Switching to state: ReplaceExtinguisherAiState");
//				bot.setAiState(new ReplaceExtinguisherAiState(bot, world));
//			}
//		} else {
			if(previousState == null || !(previousState instanceof NormalAiState)) {
				System.out.println("Switching to state: NormalAiState");
				bot.setAiState(new NormalAiState(bot, world));
			}
		}
//	}
}
