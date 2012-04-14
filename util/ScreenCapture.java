package org.powerbot.game.api.util;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.powerbot.game.bot.Bot;

public class ScreenCapture {
	private static final Logger log = Logger.getLogger(ScreenCapture.class.getName());
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");

	public static void save(final Bot bot) {
		final String name = ScreenCapture.dateFormat.format(new Date()) + ".png";
		final File dir = new File(".");
		if (dir.isDirectory() || dir.mkdirs()) {
			ScreenCapture.save(bot, new File(dir, name), "png");
		}
	}

	public static void save(final Bot bot, String fileName) {
		if (!fileName.endsWith(".png")) {
			fileName = fileName.concat(".png");
		}

		final File dir = new File(".");
		if (dir.isDirectory() || dir.mkdirs()) {
			ScreenCapture.save(bot, new File(dir, fileName), "png");
		}
	}

	private static void save(final Bot bot, final File file, final String type) {
		try {
			final BufferedImage image = capture(bot);
			ImageIO.write(image, type, file);
			log.severe("Saved screen capture as " + file.getName());
		} catch (final Exception ignored) {
			log.severe("Failed to save screen capture (" + file.getName() + ")");
		}
	}

	public static BufferedImage capture(final Bot bot) {
		final BufferedImage source = bot.getImage();
		final WritableRaster raster = source.copyData(null);
		return new BufferedImage(source.getColorModel(), raster, source.isAlphaPremultiplied(), null);
	}
}
