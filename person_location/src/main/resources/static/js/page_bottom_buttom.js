
// ПЕРЕХОД ВНИЗ СТРАНИЦІ

(function() {
    'use strict';

    let pageBottom = document.getElementById('pageBottom');

    function scrollDown() {
        let windowCoords = document.body.scrollHeight - document.body.clientHeight;

        console.log(document.body.scrollHeight);
        console.log(document.body.clientHeight);
        console.log(windowCoords);

        (function scroll() {
            if (window.pageYOffset < windowCoords) {
                window.scrollBy(0, 10);
                setTimeout(scroll, 0);
            }
            if (window.pageYOffset > windowCoords) {
                window.scrollTo(0, windowCoords);
            }
        })();
    }

    pageBottom.addEventListener('click', scrollDown);
})();