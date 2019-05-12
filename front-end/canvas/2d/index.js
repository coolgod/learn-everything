const drawing = document.getElementById('drawing');

if (drawing.getContext) {
    const context = drawing.getContext('2d');

    // row 1
    context.strokeStyle = "red";
    context.lineWidth = 5;
    context.strokeRect(0, 0, 100, 100);

    context.strokeStyle = "green";
    context.lineWidth = 1;
    context.strokeRect(111, 0, 100, 100);

    context.strokeStyle = "blue";
    context.strokeRect(221, 0, 100, 100);

    // row 2
    context.fillStyle = "#0000ff";
    context.fillRect(0, 111, 100, 100);

    context.fillStyle = "#00ff00";
    context.fillRect(111, 111, 100, 100);

    context.fillStyle = "#ff0000";
    context.fillRect(221, 111, 100, 100);

    // 
    context.fillStyle = "rgba(0, 0, 255, 1)";
    context.fillRect(111, 221, 100, 100);

    context.fillStyle = "rgba(0, 255, 0, 0.5)";
    context.fillRect(51, 271, 100, 100);

    context.fillStyle = "rgba(255, 0, 0, 0.5)";
    context.fillRect(152, 271, 100, 100);
}
