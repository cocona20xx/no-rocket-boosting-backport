package trinitysoft.norocketboost;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(NoRocketBoostMod.MODID)
public class NoRocketBoostMod {
    public static final String MODID = "no_rocket_boosting";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static <T extends GameRules.Value<T>> GameRules.Key<T> registerGameRule(String ruleName, GameRules.Category ruleCategory, GameRules.Type<T> ruleType) {
        return GameRules.register(ruleName, ruleCategory, ruleType);
    }
    public static GameRules.Type<GameRules.BooleanValue> basicBooleanRuleType(Boolean defaultValue) {
        return GameRules.BooleanValue.create(defaultValue);
    }

    public static final GameRules.Key<GameRules.BooleanValue> RULE_ROCKET_BOOSTING = NoRocketBoostMod.registerGameRule(
            "eg_EnableElytraRocketBoosting", GameRules.Category.PLAYER, NoRocketBoostMod.basicBooleanRuleType(false)
    );
    public static final GameRules.Key<GameRules.BooleanValue> RULE_PUNCH_BOOSTING = NoRocketBoostMod.registerGameRule(
            "eg_EnablePunchBowBoosting", GameRules.Category.PLAYER, NoRocketBoostMod.basicBooleanRuleType(true)
    );
}
