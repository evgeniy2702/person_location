
// GO TO THE BOTTOM OF THE PAGE

(function() {
    'use strict';

    let pageBottom = document.getElementById('pageBottom');

    function scrollDown() {
        let windowCoords = document.body.scrollHeight - document.body.clientHeight;

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