var dd = [];
function drawDot(a, b) {
    var a = parseInt(a / 9, 10) + 5,
    b = parseInt(b / 9, 10) + 5;
    dd.push([a, b])
}
function drawLine(c, g, b, f) {
    var e, a = Math.floor(Math.sqrt((b - c) * (b - c) + (f - g) * (f - g)));
    var d = Math.atan((b - c) / (f - g));
    if (((f - g) < 0 && (b - c) > 0) || ((f - g) < 0 && (b - c) < 0)) {
        d = Math.PI + d
    }
    var j = Math.sin(d),
    h = Math.cos(d);
    for (e = 0; e < a; e = e + 11) {
        drawDot(c + e * j, g + e * h)
    }
}
function show(g) {
    var d = g.split("#");
    for (var e = 0; e < d.length; e++) {
        var c = d[e].split("-");
        for (var f = 0; f < c.length - 1; f++) {
            var b = c[f].split(","),
            a = c[f + 1].split(",");
            drawLine(b[0] * 1, b[1] * 1, a[0] * 1, a[1] * 1)
        }
    }
}
var ddpar = [];
function showres(c) {
    if (timer != null) {
        clearTimeout(timer)
    }
    ddpar = [];
    var a = c.split("@");
    for (var b = 0; b < a.length; b++) {
        dd = [];
        show(a[b]);
        ddpar.push(dd)
    }
    ss(0, 0)
}
var timer = null,
lasta, tian, pos;
function ss(e, c) {
    var j = ddpar[e],
    d = [],
    h,
    g;
    for (var f = 0; f < 7; f++) {
        if (c >= j.length) {
            break
        }
        h = j[c][0];
        g = j[c][1];
        d.push("<div class='tianzi_dot' style='left: " + (h) + "px; top: " + (g) + "px;'></div>");
        c++
    }
    if (e != lasta) {
        tian = $("#tianzi_k_sx_" + e);
        pos = tian.position();
        lasta = e
    }
    tian.append(d.join(""));
    if (c < j.length - 1) {
        c++;
        timer = setTimeout(function() {
            ss(e, c)
        },
        100)
    } else {
        clearTimeout(timer);
        timer = setTimeout(function() {
            d_show_donghua()
        },
        1000)
    }
};