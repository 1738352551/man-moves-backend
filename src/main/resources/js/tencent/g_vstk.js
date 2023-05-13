function getgVstk(e) {
    for (var t = 0, n = e.length, i = 5381; t < n; ++t)
        i += (i << 5) + e.charAt(t).charCodeAt();
    return (2147483647 & i).toString();
}