# make a black and white chess

from PIL import Image, ImageDraw

def generate_two_chess(color):
    image = Image.new("RGBA", (750, 750), (0,0,0,0))
    draw = ImageDraw.Draw(image)

    bunding_box = [(0, 0), (750, 750)]

    draw.ellipse(bunding_box, fill=color)

    image.save(f".\\chess\\{color}_chess.png")

generate_two_chess('black')
generate_two_chess('white')