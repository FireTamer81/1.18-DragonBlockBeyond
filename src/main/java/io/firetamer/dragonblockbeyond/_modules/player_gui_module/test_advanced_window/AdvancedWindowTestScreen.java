package io.firetamer.dragonblockbeyond._modules.player_gui_module.test_advanced_window;

import io.firetamer.dragonblockbeyond.handlers.TextureHandler;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.advanced_window_gui.AbstractAdvancedWindow;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.advanced_window_gui.base.BasicButton;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.advanced_window_gui.base.ContentBox;
import io.firetamer.dragonblockbeyond.util.library_candidates.gui_stuff.objects.advanced_window_gui.base.ContentBoxBehaviour;
import io.firetamer.dragonblockbeyond.util.library_candidates.FireLibColor;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.ArrayList;
import java.util.List;

public class AdvancedWindowTestScreen extends AbstractAdvancedWindow {


    public AdvancedWindowTestScreen() {
        super(getTopLevelBoxes(), getTopLevelButtons());
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public int getBackgroundColor() {
        return new FireLibColor(50, 50, 50, 100).getRGBA();
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    private static List<ContentBox> getTopLevelBoxes() {
        List<ContentBox> items = new ArrayList<>();

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        items.add(testContentBox);

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        return items;
    }

    private static List<BasicButton> getTopLevelButtons() {
        List<BasicButton> items = new ArrayList<>();

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        items.add(testButton);

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        return items;
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public static final BasicButton testButton = new BasicButton(
            new ContentBoxBehaviour.BasicButtonProperties()
                    .widthPercentageOfParent(0.1f)
                    .heightPercentageOfParent(0.1f)
                    .backgroundColor(new FireLibColor(255, 0, 0), new FireLibColor(0, 255, 0))
                    .borderFont(TextureHandler.BORDER_2)
                    .position(ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_RIGHT)
                    .exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_MIDDLE)
                    .hoverColor(new FireLibColor(200, 20, 255, 150))
            ,

            new ContentBoxBehaviour.ExteriorChildren()
                    .addExteriorButtonChild(SecondLevelElements.testButton_3)
    ) {
        @Override
        public void click() {
            final var player = Minecraft.getInstance().player;
            final var level = Minecraft.getInstance().level;
            level.playSound(player, player, SoundEvents.UI_BUTTON_CLICK, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            player.displayClientMessage(new TextComponent("Text Button Works"), false);
        }
    };

    public static final ContentBox testContentBox = new ContentBox(
            new ContentBoxBehaviour.BasicBoxProperties()
                    .backgroundColor(new FireLibColor(0, 255, 255), new FireLibColor(0, 255, 0))
                    .heightPercentageOfParent(0.5f)
                    .widthPercentageOfParent(0.4f)
                    .position(ContentBoxBehaviour.InteriorContentBoxPosition.MIDDLE_LEFT)
                    //.absolutePositionOffset(-40, -40)
                    //.dynamicPositionOffset(0.2f, -0.3f)
                    .borderFont(TextureHandler.BORDER_2)
                    //.noBackground()
            ,

            new ContentBoxBehaviour.InteriorAndExteriorChildren()
                    .addInteriorChild(SecondLevelElements.testPanel)
                    .addExteriorChild(SecondLevelElements.testPanel2)
    );






    public static class SecondLevelElements {
        public static final ContentBox testPanel = new ContentBox(
                new ContentBoxBehaviour.BasicBoxProperties()
                        .backgroundColor(new FireLibColor(255, 0, 0))
                        .heightPercentageOfParent(0.2f)
                        .widthPercentageOfParent(0.8f)
                        .position(ContentBoxBehaviour.InteriorContentBoxPosition.TOP_MIDDLE)
                        //.exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_LEFT)
                        .borderFont(TextureHandler.BORDER_1)
                ,

                new ContentBoxBehaviour.InteriorAndExteriorChildren()
                        .addExteriorChild(ThirdLevelElements.testPanel1A)
        );

        public static final ContentBox testPanel2 = new ContentBox(
                new ContentBoxBehaviour.BasicBoxProperties()
                        .backgroundColor(new FireLibColor(255, 0, 0))
                        .heightPercentageOfParent(0.2f)
                        .widthPercentageOfParent(0.8f)
                        //.position(ContentBoxBehaviour.InteriorContentBoxPosition.TOP_MIDDLE)
                        .exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.RIGHT_TOP)
                        .borderFont(TextureHandler.BORDER_1)
                ,

                new ContentBoxBehaviour.InteriorAndExteriorChildren()
                        .addExteriorChild(ThirdLevelElements.testPanel2A)
        );

        public static final BasicButton testButton_3 = new BasicButton(
                new ContentBoxBehaviour.BasicButtonProperties()
                        .widthPercentageOfParent(1.0f)
                        .heightPercentageOfParent(1.0f)
                        .backgroundColor(new FireLibColor(0, 255, 0), new FireLibColor(0, 0, 255))
                        .borderFont(TextureHandler.BORDER_2)
                        .position(ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_RIGHT)
                        .exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.TOP_MIDDLE)
                        .hoverColor(new FireLibColor(200, 20, 255, 150))
                ,

                new ContentBoxBehaviour.ExteriorChildren()
        ) {
            @Override
            public void click() {
                final var player = Minecraft.getInstance().player;
                final var level = Minecraft.getInstance().level;
                level.playSound(player, player, SoundEvents.UI_BUTTON_CLICK, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
                player.displayClientMessage(new TextComponent("Text Button 2 Works!!!!!!!!!!"), false);
            }
        };
    }

    public static class ThirdLevelElements {
        public static final ContentBox testPanel1A = new ContentBox(
                new ContentBoxBehaviour.BasicBoxProperties()
                        .backgroundColor(new FireLibColor(255, 0, 255))
                        .heightPercentageOfParent(0.4f)
                        .widthPercentageOfParent(0.6f)
                        //.position(ContentBoxBehaviour.InteriorContentBoxPosition.TOP_MIDDLE)
                        .exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_MIDDLE)
                        .borderFont(TextureHandler.BORDER_2)
                ,

                new ContentBoxBehaviour.InteriorAndExteriorChildren()
        );

        public static final ContentBox testPanel2A = new ContentBox(
                new ContentBoxBehaviour.BasicBoxProperties()
                        .backgroundColor(new FireLibColor(255, 0, 255))
                        .heightPercentageOfParent(0.4f)
                        .widthPercentageOfParent(0.6f)
                        //.position(ContentBoxBehaviour.InteriorContentBoxPosition.TOP_MIDDLE)
                        .exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_MIDDLE)
                        .borderFont(TextureHandler.BORDER_2)
                ,

                new ContentBoxBehaviour.InteriorAndExteriorChildren()
                        .addExteriorButtonChild(FourthLevelElements.testButton_2)
        );
    }

    public static class FourthLevelElements {
        public static final BasicButton testButton_2 = new BasicButton(
                new ContentBoxBehaviour.BasicButtonProperties()
                        .widthPercentageOfParent(1.0f)
                        .heightPercentageOfParent(1.0f)
                        .backgroundColor(new FireLibColor(0, 255, 0), new FireLibColor(0, 0, 255))
                        .borderFont(TextureHandler.BORDER_2)
                        .position(ContentBoxBehaviour.InteriorContentBoxPosition.BOTTOM_RIGHT)
                        .exteriorPosition(ContentBoxBehaviour.ExteriorContentBoxPosition.BOTTOM_RIGHT)
                        .hoverColor(new FireLibColor(200, 20, 255, 150))
                ,

                new ContentBoxBehaviour.ExteriorChildren()
        ) {
            @Override
            public void click() {
                final var player = Minecraft.getInstance().player;
                final var level = Minecraft.getInstance().level;
                level.playSound(player, player, SoundEvents.UI_BUTTON_CLICK, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
                player.displayClientMessage(new TextComponent("Text Button 2 Works!!!!!!!!!!"), false);
            }
        };
    }
}
