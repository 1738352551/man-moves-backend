var ccy = function(t, e) {
    return t << e | t >>> 32 - e
}

var w = function(t, e) {
    var r, n, o, i, a;
    return o = 2147483648 & t,
        i = 2147483648 & e,
        a = (1073741823 & t) + (1073741823 & e),
        (r = 1073741824 & t) & (n = 1073741824 & e) ? 2147483648 ^ a ^ o ^ i : r | n ? 1073741824 & a ? 3221225472 ^ a ^ o ^ i : 1073741824 ^ a ^ o ^ i : a ^ o ^ i
}

var p = function(t, e, r, n, o, i, a) {
    return t = w(t, w(w(function(t, e, r) {
        return t & e | ~t & r
    }(e, r, n), o), a)),
        w(ccy(t, i), e)
}

var _ = function(t, e, r, n, o, i, a) {
    return t = w(t, w(w(function(t, e, r) {
        return t & r | e & ~r
    }(e, r, n), o), a)),
        w(ccy(t, i), e)
}

var x = function(t, e, r, n, o, i, a) {
    return t = w(t, w(w(function(t, e, r) {
        return t ^ e ^ r
    }(e, r, n), o), a)),
        w(ccy(t, i), e)
}

var b = function(t, e, r, n, o, i, a) {
    return t = w(t, w(w(function(t, e, r) {
        return e ^ (t | ~r)
    }(e, r, n), o), a)),
        w(ccy(t, i), e)
}

var C = function(t) {
    var e, r = "", n = "";
    for (e = 0; e <= 3; e++)
        r += (n = "0" + (t >>> 8 * e & 255).toString(16)).substr(n.length - 2, 2);
    return r
}

function g(t) {
    t += "";
    var e, r, n, o, i, a, u, c, s, f = Array();
    for (t = function(t) {
        t = t.replace(/\x0d\x0a/g, "\n");
        for (var e = "", r = 0; r < t.length; r++) {
            var n = t.charCodeAt(r);
            n < 128 ? e += String.fromCharCode(n) : n > 127 && n < 2048 ? (e += String.fromCharCode(n >> 6 | 192),
                e += String.fromCharCode(63 & n | 128)) : (e += String.fromCharCode(n >> 12 | 224),
                e += String.fromCharCode(n >> 6 & 63 | 128),
                e += String.fromCharCode(63 & n | 128))
        }
        return e
    }(t),
             f = function(t) {
                 for (var e, r = t.length, n = r + 8, o = 16 * ((n - n % 64) / 64 + 1), i = Array(o - 1), a = 0, u = 0; u < r; )
                     a = u % 4 * 8,
                         i[e = (u - u % 4) / 4] = i[e] | t.charCodeAt(u) << a,
                         u++;
                 return a = u % 4 * 8,
                     i[e = (u - u % 4) / 4] = i[e] | 128 << a,
                     i[o - 2] = r << 3,
                     i[o - 1] = r >>> 29,
                     i
             }(t),
             a = 1732584193,
             u = 4023233417,
             c = 2562383102,
             s = 271733878,
             e = 0; e < f.length; e += 16)
        r = a,
            n = u,
            o = c,
            i = s,
            a = p(a, u, c, s, f[e + 0], 7, 3614090360),
            s = p(s, a, u, c, f[e + 1], 12, 3905402710),
            c = p(c, s, a, u, f[e + 2], 17, 606105819),
            u = p(u, c, s, a, f[e + 3], 22, 3250441966),
            a = p(a, u, c, s, f[e + 4], 7, 4118548399),
            s = p(s, a, u, c, f[e + 5], 12, 1200080426),
            c = p(c, s, a, u, f[e + 6], 17, 2821735955),
            u = p(u, c, s, a, f[e + 7], 22, 4249261313),
            a = p(a, u, c, s, f[e + 8], 7, 1770035416),
            s = p(s, a, u, c, f[e + 9], 12, 2336552879),
            c = p(c, s, a, u, f[e + 10], 17, 4294925233),
            u = p(u, c, s, a, f[e + 11], 22, 2304563134),
            a = p(a, u, c, s, f[e + 12], 7, 1804603682),
            s = p(s, a, u, c, f[e + 13], 12, 4254626195),
            c = p(c, s, a, u, f[e + 14], 17, 2792965006),
            u = p(u, c, s, a, f[e + 15], 22, 1236535329),
            a = _(a, u, c, s, f[e + 1], 5, 4129170786),
            s = _(s, a, u, c, f[e + 6], 9, 3225465664),
            c = _(c, s, a, u, f[e + 11], 14, 643717713),
            u = _(u, c, s, a, f[e + 0], 20, 3921069994),
            a = _(a, u, c, s, f[e + 5], 5, 3593408605),
            s = _(s, a, u, c, f[e + 10], 9, 38016083),
            c = _(c, s, a, u, f[e + 15], 14, 3634488961),
            u = _(u, c, s, a, f[e + 4], 20, 3889429448),
            a = _(a, u, c, s, f[e + 9], 5, 568446438),
            s = _(s, a, u, c, f[e + 14], 9, 3275163606),
            c = _(c, s, a, u, f[e + 3], 14, 4107603335),
            u = _(u, c, s, a, f[e + 8], 20, 1163531501),
            a = _(a, u, c, s, f[e + 13], 5, 2850285829),
            s = _(s, a, u, c, f[e + 2], 9, 4243563512),
            c = _(c, s, a, u, f[e + 7], 14, 1735328473),
            u = _(u, c, s, a, f[e + 12], 20, 2368359562),
            a = x(a, u, c, s, f[e + 5], 4, 4294588738),
            s = x(s, a, u, c, f[e + 8], 11, 2272392833),
            c = x(c, s, a, u, f[e + 11], 16, 1839030562),
            u = x(u, c, s, a, f[e + 14], 23, 4259657740),
            a = x(a, u, c, s, f[e + 1], 4, 2763975236),
            s = x(s, a, u, c, f[e + 4], 11, 1272893353),
            c = x(c, s, a, u, f[e + 7], 16, 4139469664),
            u = x(u, c, s, a, f[e + 10], 23, 3200236656),
            a = x(a, u, c, s, f[e + 13], 4, 681279174),
            s = x(s, a, u, c, f[e + 0], 11, 3936430074),
            c = x(c, s, a, u, f[e + 3], 16, 3572445317),
            u = x(u, c, s, a, f[e + 6], 23, 76029189),
            a = x(a, u, c, s, f[e + 9], 4, 3654602809),
            s = x(s, a, u, c, f[e + 12], 11, 3873151461),
            c = x(c, s, a, u, f[e + 15], 16, 530742520),
            u = x(u, c, s, a, f[e + 2], 23, 3299628645),
            a = b(a, u, c, s, f[e + 0], 6, 4096336452),
            s = b(s, a, u, c, f[e + 7], 10, 1126891415),
            c = b(c, s, a, u, f[e + 14], 15, 2878612391),
            u = b(u, c, s, a, f[e + 5], 21, 4237533241),
            a = b(a, u, c, s, f[e + 12], 6, 1700485571),
            s = b(s, a, u, c, f[e + 3], 10, 2399980690),
            c = b(c, s, a, u, f[e + 10], 15, 4293915773),
            u = b(u, c, s, a, f[e + 1], 21, 2240044497),
            a = b(a, u, c, s, f[e + 8], 6, 1873313359),
            s = b(s, a, u, c, f[e + 15], 10, 4264355552),
            c = b(c, s, a, u, f[e + 6], 15, 2734768916),
            u = b(u, c, s, a, f[e + 13], 21, 1309151649),
            a = b(a, u, c, s, f[e + 4], 6, 4149444226),
            s = b(s, a, u, c, f[e + 11], 10, 3174756917),
            c = b(c, s, a, u, f[e + 2], 15, 718787259),
            u = b(u, c, s, a, f[e + 9], 21, 3951481745),
            a = w(a, r),
            u = w(u, n),
            c = w(c, o),
            s = w(s, i);
    return (C(a) + C(u) + C(c) + C(s)).toLowerCase()
}



// app_version
//     :
//     "4.0.0"
// auth_cookie
//     :
//     ""
// device_id
//     :
//     "07d3c3aed6cb71b6622f2c7f7ea479fa"
// entity_id
//     :
//     2827333801193900
// sign
//     :
//     "CCA6BDFA5BE74D9637DFE691103326CD"
// src
//     :
//     "pcw_tvg"
// timestamp
//     :
//     1683966189381
// user_id
//     :
//     ""
// vip_status
//     :
//     0
// vip_type
//     :
//     ""
function N() {
    var t = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {}
        , e = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : {}
        , n = e.splitKey
        , r = void 0 === n ? "&" : n
        , o = e.secretKey
        , c = void 0 === o ? 'howcuteitis' : o
        , l = e.key
        , d = void 0 === l ? "secret_key" : l
        , h = Object.keys(t).sort()
        , v = h.map((function(e) {
            return "".concat(e, "=").concat(t[e])
        }
    ));
    return v.push("".concat(d, "=").concat(c)),
        g(v.join(r))
}
// "agentType=1012&businessType=14&entityId=2827333801193900&f_userId=0&m_device_id=07d3c3aed6cb71b6622f2c7f7ea479fa&timestamp=1683966976819&userId=0LdsifCI^$12*$6^.q(1"
// "app_version=4.0.0&auth_cookie=&device_id=07d3c3aed6cb71b6622f2c7f7ea479fa&entity_id=2827333801193900&src=pcw_tvg&timestamp=1683967069717&user_id=&vip_status=0&vip_type=&secret_key=2827333801193900"
// y = 2827333801193900;
// O = {
//     entity_id: y,
//     timestamp: (new Date).getTime(),
//     src: "pcw_tvg",
//     vip_status: 0,
//     vip_type:  "",
//     auth_cookie: "",
//     device_id: "07d3c3aed6cb71b6622f2c7f7ea479fa",
//     user_id: "",
//     app_version: '4.0.0'
// }
// j = N(O).toUpperCase()
// O.sign = j


function getMd5(entityId) {
    O = {
        entity_id: entityId,
        timestamp: (new Date).getTime(),
        src: "pcw_tvg",
        vip_status: 0,
        vip_type:  "",
        auth_cookie: "",
        device_id: "07d3c3aed6cb71b6622f2c7f7ea479fa",
        user_id: "",
        app_version: '4.0.0'
    }
    j = N(O).toUpperCase()
    return j + '|' + O.timestamp;
}