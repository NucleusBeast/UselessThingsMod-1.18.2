package net.nucleusbeast.uselessthings.item.advanced;

import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.nucleusbeast.uselessthings.util.TextFile;

import java.util.List;
import java.util.Random;

public class MajesticStick extends Item {

    List<String> message;

    public MajesticStick(Settings settings) {
        super(settings);
        message = TextFile.Load();
    }



    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient()){
            PlayerEntity player = context.getPlayer();
            if (player != null){
                Random rand = new Random();
                int roll = rand.nextInt(message.size());
                if (message.get(roll).contains("%player%")){
                    int index =  message.get(roll).indexOf("%");
                    player.sendMessage(new LiteralText(message.get(roll).substring(0, index) + player.getName().asString() + message.get(roll).substring(index + 8)), false);
                }
                else{
                    player.sendMessage(new LiteralText(message.get(roll)), false);
                }
            }
        }

        context.getStack().damage(1, context.getPlayer(), (player) -> player.sendToolBreakStatus(player.getActiveHand()));
        return super.useOnBlock(context);
    }
}
