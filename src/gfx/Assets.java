
package gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {

	public static Font font30, font40, font25;

	public static BufferedImage player, grassTile, rock, blackTile, tree, whitecat, blackcat, yellowcat, stone, apple,
			house, mushroom, castle, statue;
	public static BufferedImage cutTree, bush, pond, skeleton, log, village, ship, cheese, lemon, sword, shop, flower,
			wing, carrot, meat, drink, pizza, icecream;
	public static BufferedImage sushi, potato, egg, bacon, fish, chicken, watermelon, prawn;
	public static BufferedImage[] path, coin, flag;
	public static BufferedImage[] btn_start;
	public static BufferedImage[] whitecat_down, whitecat_up, whitecat_left, whitecat_right;
	public static BufferedImage[] blackcat_down, blackcat_up, blackcat_left, blackcat_right;
	public static BufferedImage[] yellowcat_down, yellowcat_up, yellowcat_left, yellowcat_right;
	public static BufferedImage inventoryScreen, infoScreen;
	public static BufferedImage[] fountain;

	public static void init() {
		font30 = FontLoader.loadFont("Resources/Fonts/slkscr.ttf", 30);
		font40 = FontLoader.loadFont("Resources/Fonts/slkscr.ttf", 40);
		font25 = FontLoader.loadFont("Resources/Fonts/slkscr.ttf", 25);

		SpriteSheet overworld = new SpriteSheet(ImageLoader.loadImage("/textures/Overworld.png"));
		SpriteSheet grass = new SpriteSheet(ImageLoader.loadImage("/textures/Grass.png"));
		SpriteSheet test = new SpriteSheet(ImageLoader.loadImage("/textures/TestWorld.jpg"));
		SpriteSheet pondsheet = new SpriteSheet(ImageLoader.loadImage("/textures/Pond.png"));
		SpriteSheet food1 = new SpriteSheet(ImageLoader.loadImage("/textures/Food1.png"));
		SpriteSheet coinsheet = new SpriteSheet(ImageLoader.loadImage("/textures/Coin.png"));
		SpriteSheet food2 = new SpriteSheet(ImageLoader.loadImage("/textures/Food2.png"));
		SpriteSheet food3 = new SpriteSheet(ImageLoader.loadImage("/textures/Food3.png"));

		whitecat_down = new BufferedImage[2];
		whitecat_up = new BufferedImage[2];
		whitecat_left = new BufferedImage[2];
		whitecat_right = new BufferedImage[2];

		whitecat_down[0] = ImageLoader.loadImage("/textures/CatDown1.png");
		whitecat_down[1] = ImageLoader.loadImage("/textures/CatDown2.png");
		whitecat_up[0] = ImageLoader.loadImage("/textures/CatUp1.png");
		whitecat_up[1] = ImageLoader.loadImage("/textures/CatUp2.png");
		whitecat_right[0] = ImageLoader.loadImage("/textures/CatRight1.png");
		whitecat_right[1] = ImageLoader.loadImage("/textures/CatRight2.png");
		whitecat_left[0] = ImageLoader.loadImage("/textures/CatLeft1.png");
		whitecat_left[1] = ImageLoader.loadImage("/textures/CatLeft2.png");
		whitecat = ImageLoader.loadImage("/textures/CatStand.png");

		blackcat_down = new BufferedImage[2];
		blackcat_up = new BufferedImage[2];
		blackcat_left = new BufferedImage[2];
		blackcat_right = new BufferedImage[2];

		blackcat_down[0] = ImageLoader.loadImage("/textures/BCatDown1.png");
		blackcat_down[1] = ImageLoader.loadImage("/textures/BCatDown2.png");
		blackcat_up[0] = ImageLoader.loadImage("/textures/BCatUp1.png");
		blackcat_up[1] = ImageLoader.loadImage("/textures/BCatUp2.png");
		blackcat_right[0] = ImageLoader.loadImage("/textures/BCatRight1.png");
		blackcat_right[1] = ImageLoader.loadImage("/textures/BCatRight2.png");
		blackcat_left[0] = ImageLoader.loadImage("/textures/BCatLeft1.png");
		blackcat_left[1] = ImageLoader.loadImage("/textures/BCatLeft2.png");
		blackcat = ImageLoader.loadImage("/textures/BCatStand.png");

		yellowcat_down = new BufferedImage[2];
		yellowcat_up = new BufferedImage[2];
		yellowcat_left = new BufferedImage[2];
		yellowcat_right = new BufferedImage[2];

		yellowcat_down[0] = ImageLoader.loadImage("/textures/YCatDown1.png");
		yellowcat_down[1] = ImageLoader.loadImage("/textures/YCatDown2.png");
		yellowcat_up[0] = ImageLoader.loadImage("/textures/YCatUp1.png");
		yellowcat_up[1] = ImageLoader.loadImage("/textures/YCatUp2.png");
		yellowcat_right[0] = ImageLoader.loadImage("/textures/YCatRight1.png");
		yellowcat_right[1] = ImageLoader.loadImage("/textures/YCatRight2.png");
		yellowcat_left[0] = ImageLoader.loadImage("/textures/YCatLeft1.png");
		yellowcat_left[1] = ImageLoader.loadImage("/textures/YCatLeft2.png");
		yellowcat = ImageLoader.loadImage("/textures/YCatDownStand.png");

		inventoryScreen = ImageLoader.loadImage("/textures/inventoryScreen.png");
		infoScreen = ImageLoader.loadImage("/textures/infoScreen.png");
		tree = ImageLoader.loadImage("/textures/Tree.png");
		house = overworld.crop(96, 0, 80, 80);
		grassTile = grass.crop(0, 0, 32, 32);
		blackTile = ImageLoader.loadImage("/textures/TestTrees.png");
		stone = overworld.crop(64, 16, 32, 32);
		apple = ImageLoader.loadImage("/textures/apple.png");
		mushroom = ImageLoader.loadImage("/textures/Mushroom.png");
		castle = overworld.crop(64, 496, 64, 57);
		statue = overworld.crop(128, 496, 32, 47);
		cutTree = overworld.crop(497, 54, 31, 26);
		bush = ImageLoader.loadImage("/textures/Bush.png");
		pond = pondsheet.crop(8, 70, 82, 79);
		skeleton = overworld.crop(434, 51, 13, 12);
		log = overworld.crop(50, 80, 46, 16);
		village = overworld.crop(208, 85, 32, 43);
		ship = ImageLoader.loadImage("/textures/Ship.png");
		cheese = food1.crop(35, 2, 40, 34);
		lemon = food1.crop(79, 3, 39, 34);
		sword = ImageLoader.loadImage("/textures/Levelup.png");
		shop = overworld.crop(289, 359, 79, 86);
		flower = overworld.crop(417, 320, 63, 23);
		wing = ImageLoader.loadImage("/textures/Wing.png");
		carrot = food1.crop(118, 0, 42, 42);
		meat = food1.crop(158, 0, 42, 42);
		drink = food2.crop(17, 0, 14, 16);
		pizza = food2.crop(31, 0, 18, 16);
		icecream = food2.crop(49, 0, 15, 16);
		sushi = food3.crop(112, 0, 16, 16);
		potato = food3.crop(112, 16, 16, 16);
		egg = food3.crop(0, 32, 16, 16);
		bacon = food3.crop(48, 32, 16, 16);
		fish = food3.crop(112, 32, 16, 16);
		chicken = food3.crop(16, 48, 16, 16);
		watermelon = food3.crop(0, 96, 16, 16);
		prawn = food3.crop(112, 112, 16, 16);

		fountain = new BufferedImage[3];
		fountain[0] = overworld.crop(400, 144, 48, 47);
		fountain[1] = overworld.crop(448, 144, 48, 47);
		fountain[2] = overworld.crop(352, 144, 48, 47);

		coin = new BufferedImage[4];
		coin[0] = coinsheet.crop(2, 66, 11, 11);
		coin[1] = coinsheet.crop(18, 66, 11, 11);
		coin[2] = coinsheet.crop(34, 66, 11, 11);
		coin[3] = coinsheet.crop(51, 66, 11, 11);

		flag = new BufferedImage[5];
		flag[0] = overworld.crop(54, 464, 32, 32);
		flag[1] = overworld.crop(86, 464, 32, 32);
		flag[2] = overworld.crop(118, 464, 32, 32);
		flag[3] = overworld.crop(150, 464, 32, 32);
		flag[4] = overworld.crop(182, 464, 32, 32);

		path = new BufferedImage[50];
		path[0] = test.crop(1, 496, 32, 32);
		path[1] = test.crop(34, 529, 32, 32);
		path[2] = test.crop(67, 496, 32, 32);
		path[3] = test.crop(34, 463, 32, 32);
		path[4] = test.crop(1, 463, 32, 32);
		path[5] = test.crop(67, 463, 32, 32);
		path[6] = ImageLoader.loadImage("/textures/Path1.png");
		path[7] = ImageLoader.loadImage("/textures/Path2.png");
		path[8] = ImageLoader.loadImage("/textures/Path3.png");
		path[9] = ImageLoader.loadImage("/textures/Path4.png");
		path[10] = test.crop(40, 505, 16, 16);
		path[11] = test.crop(1, 364, 32, 32);
		path[12] = test.crop(34, 364, 32, 32);
		path[13] = test.crop(67, 364, 32, 32);
		path[14] = test.crop(67, 397, 32, 32);
		path[15] = test.crop(67, 430, 32, 32);
		path[16] = test.crop(34, 430, 32, 32);
		path[17] = test.crop(1, 430, 32, 32);
		path[18] = test.crop(1, 397, 32, 32);
		path[19] = test.crop(133, 463, 32, 32);
		path[20] = ImageLoader.loadImage("/textures/Path5.png");
		path[21] = test.crop(232, 364, 32, 32);
		path[22] = ImageLoader.loadImage("/textures/Path6.png");
		path[23] = test.crop(331, 1, 32, 32);
		path[24] = test.crop(232, 133, 32, 32);
		path[25] = test.crop(298, 232, 32, 32);
		path[26] = test.crop(265, 265, 32, 32);
		path[27] = test.crop(331, 265, 32, 32);
		path[28] = test.crop(529, 529, 32, 32);
		path[29] = test.crop(529, 463, 32, 32);
		path[30] = test.crop(562, 298, 32, 32);
		path[31] = test.crop(133, 100, 32, 32);
		path[32] = test.crop(133, 67, 32, 32);
		path[33] = test.crop(199, 100, 32, 32);
		path[34] = test.crop(133, 34, 32, 32);

	}
}
