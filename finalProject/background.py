from PIL import Image, ImageDraw

def generate_gomoku_background(image_size=750, line_color=(0, 0, 0), board_color=(240, 220, 180)):
    """
    Generate a Go-Moku background with a 15x15 grid.

    :param image_size: Size of the image in pixels (image will be square).
    :param grid_size: Number of rows and columns (15 for Go-Moku).
    :param line_color: Color of the grid lines (default black).
    :param board_color: Color of the board background (default light wood color).
    :return: None. Saves the image as 'gomoku_background.png'.
    """
    # Create a blank image with a wooden board color
    image = Image.new("RGB", (image_size, image_size), board_color)
    draw = ImageDraw.Draw(image)

    t = 49
    
    for j in range(int((750-t*14)/2), 750, t):
        draw.line([(0, j), (750, j)], fill=line_color, width=2)
        draw.line([(j, 0), (j, 750)], fill=line_color, width=2)

    # Save the image
    image.save(".\\background\\background.png")
    print("Go-Moku background saved as 'background.png'.")

# Generate the Go-Moku background
generate_gomoku_background()
