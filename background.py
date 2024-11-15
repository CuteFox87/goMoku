from PIL import Image, ImageDraw

def generate_gomoku_background(image_size=750, line_color=(0, 0, 0), board_color=(240, 220, 180)):

    image = Image.new("RGB", (image_size, image_size), board_color)
    draw = ImageDraw.Draw(image)

    t = 49
    
    for j in range(int((750-t*14)/2), 750, t):
        draw.line([(0, j), (750, j)], fill=line_color, width=2)
        draw.line([(j, 0), (j, 750)], fill=line_color, width=2)

    image.save(".\\background\\background.png")

generate_gomoku_background()
