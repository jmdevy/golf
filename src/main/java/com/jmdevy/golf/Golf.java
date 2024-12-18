package com.jmdevy.golf;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientRegistryLayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import com.jmdevy.golf.client.keybindings.HitBallKeyBindings;
import com.jmdevy.golf.common.blocks.Fairway;
import com.jmdevy.golf.common.blocks.Fringe;
import com.jmdevy.golf.common.blocks.Green;
import com.jmdevy.golf.common.commands.SpawnGolfBallEntityCommand;
import com.jmdevy.golf.common.entities.GolfBallEntity;
import com.jmdevy.golf.common.entities.GolfBallEntityModel;
import com.jmdevy.golf.common.entities.GolfBallEntityRenderer;
import com.jmdevy.golf.common.items.GolfBallItem;






// The value here should match an entry in the META-INF/mods.toml file
@Mod(Golf.MODID)
public class Golf
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "golf";


    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static final RegistryObject<EntityType<GolfBallEntity>> GOLF_BALL = ENTITY_TYPES.register("golf_ball", () -> EntityType.Builder.<GolfBallEntity>of(GolfBallEntity::new, MobCategory.MISC) .sized(1.0F, 1.0F).build(new ResourceLocation(MODID, "golf_ball").toString()) );


    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    public static final RegistryObject<Block> FAIRWAY_BLOCK = BLOCKS.register("fairway",       () -> new Fairway(Block.Properties.copy(Blocks.DIRT_PATH).noOcclusion()));
    public static final RegistryObject<Block> FRINGE_BLOCK  = BLOCKS.register("fringe",        () -> new Fringe(Block.Properties.copy(Blocks.DIRT)));
    public static final RegistryObject<Block> GREEN_BLOCK   = BLOCKS.register("green",         () -> new Green(Block.Properties.copy(Blocks.DIRT)));

    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> FAIRWAY_BLOCK_ITEM = ITEMS.register("fairway",       () -> new BlockItem(FAIRWAY_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> FRINGE_BLOCK_ITEM  = ITEMS.register("fringe",        () -> new BlockItem(FRINGE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_BLOCK_ITEM   = ITEMS.register("green",         () -> new BlockItem(GREEN_BLOCK.get(), new Item.Properties()));


    public static final RegistryObject<Item> GOLF_BALL_ITEM = ITEMS.register("golf_ball", 
    () -> new GolfBallItem(new Item.Properties()));


    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> GOLF_BALL_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(GOLF_BALL_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    public Golf(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ENTITY_TYPES.register(modEventBus);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(EXAMPLE_BLOCK_ITEM);
            event.accept(FAIRWAY_BLOCK_ITEM);
            event.accept(FRINGE_BLOCK_ITEM);
            event.accept(GREEN_BLOCK_ITEM);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            
        }

        @SubscribeEvent
        public void registerBindings(RegisterKeyMappingsEvent event) {
            event.register(HitBallKeyBindings.forwardKey);
            event.register(HitBallKeyBindings.backKey);
            event.register(HitBallKeyBindings.leftKey);
            event.register(HitBallKeyBindings.rightKey);
            event.register(HitBallKeyBindings.spaceKey);
            event.register(HitBallKeyBindings.rightMouseKey);
            event.register(HitBallKeyBindings.leftMouseKey);
        }

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            LOGGER.info("REGISTERING RENDERERS");
            event.registerEntityRenderer(GOLF_BALL.get(), GolfBallEntityRenderer::new);
        }

        @SubscribeEvent
        public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            LOGGER.info("REGISTERING MODELS");
            event.registerLayerDefinition(GolfBallEntityRenderer.MODEL_LAYER, GolfBallEntityModel::createBodyLayer);
        }
    }

    @SubscribeEvent public void onRegisterCommands(RegisterCommandsEvent event) {
        SpawnGolfBallEntityCommand.register(event.getDispatcher());
    }
}
