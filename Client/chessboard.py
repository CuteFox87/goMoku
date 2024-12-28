import matplotlib.pyplot as plt
import numpy as np

# Define canvas size and background color
canvas_width, canvas_height = 800, 400
background_color = "#33cc33"  # Bright green

# Function to draw a pixel art Pikachu-like character
def draw_pikachu(ax, x, y, size=1):
    body_color = "#FFD700"  # Pikachu's yellow
    cheek_color = "#FF4500"  # Red for cheeks
    eye_color = "#000000"  # Black for eyes
    body_outline = "#000000"  # Black outline
    
    # Body (circle)
    body = plt.Circle((x, y), 10 * size, color=body_color, ec=body_outline, lw=1.5)
    ax.add_patch(body)
    
    # Eyes (two small circles)
    eye_left = plt.Circle((x - 5 * size, y + 5 * size), 2 * size, color=eye_color)
    eye_right = plt.Circle((x + 5 * size, y + 5 * size), 2 * size, color=eye_color)
    ax.add_patch(eye_left)
    ax.add_patch(eye_right)
    
    # Cheeks (two red circles)
    cheek_left = plt.Circle((x - 7 * size, y - 2 * size), 3 * size, color=cheek_color)
    cheek_right = plt.Circle((x + 7 * size, y - 2 * size), 3 * size, color=cheek_color)
    ax.add_patch(cheek_left)
    ax.add_patch(cheek_right)
    
    # Mouth (a line)
    ax.plot([x - 3 * size, x, x + 3 * size], [y - 5 * size, y - 7 * size, y - 5 * size], color=body_outline, lw=1)

# Initialize the figure and axis
fig, ax = plt.subplots(figsize=(10, 5))
ax.set_xlim(0, canvas_width)
ax.set_ylim(0, canvas_height)
ax.set_aspect("equal")
ax.axis("off")
ax.set_facecolor(background_color)

# Draw a repeating pattern of "Pikachu-like" characters
for i in range(5):  # Rows
    for j in range(10):  # Columns
        x = j * 80 + 40  # Adjust spacing
        y = i * 80 + 40
        draw_pikachu(ax, x, y, size=2)

# Add some text to simulate a title in pixelated style
ax.text(400, 350, "GO! GO!", color="#F00000", fontsize=24, fontweight="bold", ha="center", va="center", family="monospace")
ax.text(400, 320, "GO MOKU", color="#000000", fontsize=16, fontweight="bold", ha="center", va="center", family="monospace")

# Save and display the result
plt.tight_layout()
plt.show()
