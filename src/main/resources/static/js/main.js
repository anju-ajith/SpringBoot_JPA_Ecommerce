/*  ---------------------------------------------------
Template Name: Ashion
Description: Ashion ecommerce template
Author: Colorib
Author URI: https://colorlib.com/
Version: 1.0
Created: Colorib
---------------------------------------------------------  */

'use strict';

(function($) {

    /*------------------
        Preloader
    --------------------*/
    $(window).on('load', function() {
        $(".loader").fadeOut();
        $("#preloder").delay(200).fadeOut("slow");

        /*------------------
            Product filter
        --------------------*/
        $('.filter__controls li').on('click', function() {
            $('.filter__controls li').removeClass('active');
            $(this).addClass('active');
        });
        if ($('.property__gallery').length > 0) {
            var containerEl = document.querySelector('.property__gallery');
            var mixer = mixitup(containerEl);
        }
    });

    /*------------------
        Background Set
    --------------------*/
    $('.set-bg').each(function() {
        var bg = $(this).data('setbg');
        $(this).css('background-image', 'url(' + bg + ')');
    });

    //Search Switch
    $('.search-switch').on('click', function() {
        $('.search-model').fadeIn(400);
    });

    $('.search-close-switch').on('click', function() {
        $('.search-model').fadeOut(400, function() {
            $('#search-input').val('');
        });
    });

    //Canvas Menu
    $(".canvas__open").on('click', function() {
        $(".offcanvas-menu-wrapper").addClass("active");
        $(".offcanvas-menu-overlay").addClass("active");
    });

    $(".offcanvas-menu-overlay, .offcanvas__close").on('click', function() {
        $(".offcanvas-menu-wrapper").removeClass("active");
        $(".offcanvas-menu-overlay").removeClass("active");
    });

    /*------------------
        Navigation
    --------------------*/
    $(".header__menu").slicknav({
        prependTo: '#mobile-menu-wrap',
        allowParentLinks: true
    });

    /*------------------
        Accordin Active
    --------------------*/
    $('.collapse').on('shown.bs.collapse', function() {
        $(this).prev().addClass('active');
    });

    $('.collapse').on('hidden.bs.collapse', function() {
        $(this).prev().removeClass('active');
    });

    /*--------------------------
        Banner Slider
    ----------------------------*/
    $(".banner__slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 1,
        dots: true,
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true
    });

    /*--------------------------
        Product Details Slider
    ----------------------------*/
    $(".product__details__pic__slider").owlCarousel({
        loop: false,
        margin: 0,
        items: 1,
        dots: false,
        nav: true,
        navText: ["<i class='arrow_carrot-left'></i>", "<i class='arrow_carrot-right'></i>"],
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: false,
        mouseDrag: false,
        startPosition: 'URLHash'
    }).on('changed.owl.carousel', function(event) {
        var indexNum = event.item.index + 1;
        product_thumbs(indexNum);
    });

    function product_thumbs(num) {
        var thumbs = document.querySelectorAll('.product__thumb a');
        thumbs.forEach(function(e) {
            e.classList.remove("active");
            if (e.hash.split("-")[1] == num) {
                e.classList.add("active");
            }
        })
    }


    /*------------------
        Magnific
    --------------------*/
    $('.image-popup').magnificPopup({
        type: 'image'
    });


    $(".nice-scroll").niceScroll({
        cursorborder: "",
        cursorcolor: "#dddddd",
        boxzoom: false,
        cursorwidth: 5,
        background: 'rgba(0, 0, 0, 0.2)',
        cursorborderradius: 50,
        horizrailenabled: false
    });

    /*------------------
        CountDown
    --------------------*/
    // For demo preview start
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();

    if (mm == 12) {
        mm = '01';
        yyyy = yyyy + 1;
    } else {
        mm = parseInt(mm) + 1;
        mm = String(mm).padStart(2, '0');
    }
    var timerdate = mm + '/' + dd + '/' + yyyy;
    // For demo preview end


    // Uncomment below and use your date //

    /* var timerdate = "2020/12/30" */

    $("#countdown-time").countdown(timerdate, function(event) {
        $(this).html(event.strftime("<div class='countdown__item'><span>%D</span> <p>Day</p> </div>" + "<div class='countdown__item'><span>%H</span> <p>Hour</p> </div>" + "<div class='countdown__item'><span>%M</span> <p>Min</p> </div>" + "<div class='countdown__item'><span>%S</span> <p>Sec</p> </div>"));
    });

    /*-------------------
        Range Slider
    --------------------- */
    var rangeSlider = $(".price-range"),
        minamount = $("#minamount"),
        maxamount = $("#maxamount"),
        minPrice = rangeSlider.data('min'),
        maxPrice = rangeSlider.data('max');

    let startMin = parseInt(document.getElementById("minPrice").value) || minPrice;
    let startMax = parseInt(document.getElementById("maxPrice").value) || maxPrice;

    rangeSlider.slider({
        range: true,
        min: minPrice,
        max: maxPrice,
        values: [startMin, startMax],
        slide: function(event, ui) {
            minamount.val('₹' + ui.values[0]);
            maxamount.val('₹' + ui.values[1]);
            document.getElementById("minPrice").value = ui.values[0];
            document.getElementById("maxPrice").value = ui.values[1];

        },
        /*change: function(event, ui) {
                applyFilters(ui.values[0], ui.values[1]);
            }*/
    });
    minamount.val('₹' + startMin);
    maxamount.val('₹' + startMax);

    /*------------------
        Single Product
    --------------------*/
    $('.product__thumb .pt').on('click', function() {
        var imgurl = $(this).data('imgbigurl');
        var bigImg = $('.product__big__img').attr('src');
        if (imgurl != bigImg) {
            $('.product__big__img').attr({ src: imgurl });
        }
    });

    /*-------------------
        Quantity change
    --------------------- */
    var proQty = $('.pro-qty');
    proQty.prepend('<span class="dec qtybtn">-</span>');
    proQty.append('<span class="inc qtybtn">+</span>');
    proQty.on('click', '.qtybtn', function() {
        var $button = $(this);
        var oldValue = $button.parent().find('input').val();
        if ($button.hasClass('inc')) {
            var newVal = parseFloat(oldValue) + 1;
        } else {
            // Don't allow decrementing below zero
            if (oldValue > 0) {
                var newVal = parseFloat(oldValue) - 1;
            } else {
                newVal = 0;
            }
        }
        $button.parent().find('input').val(newVal);
    });

    /*-------------------
        Radio Btn
    --------------------- */
    $(".size__btn label").on('click', function() {
        $(".size__btn label").removeClass('active');
        $(this).addClass('active');
    });

})(jQuery);

/*------------------------
remove from wishlist
-------------------------*/

function removeFromWishlist(element) {


    let wishlistId = element.getAttribute("data-id");


    fetch('/removeWishlist/' + wishlistId, {
        method: 'DELETE',
		headers: getCsrfHeaders()
    })
        .then(response => {
            if (response.ok) {


                let item = element.closest(".product__item");

                if (item) {
                    item.style.transition = "0.3s";
                    item.style.opacity = "0";

                    setTimeout(() => {
                        item.remove();
                        location.reload();
                    }, 300);
                }

            } else {
                alert("Failed to remove item");
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}
/*------------------------
Add to  wishlist
-------------------------*/
function addToWishlist(element) {


    if (!isLoggedIn()) {

        window.location.href = "/userLoginPage";
        return;
    }


    let wishlistCount = parseInt($("#wishlist_count").text());
    let productId = element.getAttribute("data-id");



    fetch('/addToWishlist/' + productId, {
        method: 'POST',
		headers: getCsrfHeaders()
    })
        .then(response => response.text())
        .then(result => {

            if (result === "added") {


                element.classList.remove("icon_heart_alt");
                element.classList.add("icon_heart");

                /* $(element).removeClass("icon_heart_alt").addClass("icon_heart");*/

                wishlistCount++;
                $("#wishlist_count").text(wishlistCount);

            } else if (result === "removed") {


                element.classList.add("icon_heart_alt");
                element.classList.remove("icon_heart");

                /*  $(element).removeClass("icon_heart").addClass("icon_heart_alt");*/

                wishlistCount--;
                $("#wishlist_count").text(wishlistCount);
            }

        })
        .catch(error => console.error("Error:", error));
}


/*------------------------
Add to  cart from wishlist
-------------------------*/
function addToCartFromWishlist(element) {


    let wishlistId = element.getAttribute("data-id");


    fetch('/addToCartFromWishlist/' + wishlistId, {
        method: 'POST',
		headers: getCsrfHeaders()
    })
        .then(response => {
            if (response.ok) {


                let item = element.closest(".product__item");

                if (item) {
                    item.style.transition = "0.3s";
                    item.style.opacity = "0";

                    setTimeout(() => {
                        item.remove();
                        location.reload();
                    }, 300);
                }

            } else {
                alert("Failed to remove item");
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

/*------------------------
Add to  cart 
-------------------------*/

function addToCart(element) {


    if (!isLoggedIn()) {
        window.location.href = "/userLoginPage";
        return;
    }
    /*shoppingCart*/
    let cartCount = parseInt($("#shoppingCart").text());
    cartCount = cartCount + 1;
    $("#shoppingCart").text(cartCount);

    let productId = element.getAttribute("data-id");


    fetch('/addToCart/' + productId, {
        method: 'POST',
		headers: getCsrfHeaders()
    })
        .then(response => {
            if (response.ok) {
                let toast = document.getElementById("cartToast");
                toast.classList.add("show");

                setTimeout(() => {
                    toast.classList.remove("show");
                }, 2000);

            } else {
                alert("Failed to add cart");
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}


/*------------------------
delete from cart
-------------------------*/

function deleteFromCart(element) {


    let cartId = element.getAttribute("data-id");


    fetch('/removeFromCart/' + cartId, {
        method: 'DELETE',
		headers: getCsrfHeaders()
    })
        .then(response => {
            if (response.ok) {


                let item = element.closest(".cart_item");

                if (item) {
                    item.style.transition = "0.3s";
                    item.style.opacity = "0";

                    setTimeout(() => {
                        item.remove();
                        location.reload();

                    }, 300);
                }

            } else {
                alert("Failed to remove item");
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

/*user not loggedIn*/

function isLoggedIn() {
    return document.body.getAttribute("data-user") === "true";
}


function getCsrfHeaders() {
    return {
        [document.querySelector("meta[name='_csrf_header']").content]:
        document.querySelector("meta[name='_csrf']").content
    };
}


/*filter*/
/*function applyFilters(min = null, max = null) {
	
    let colors = [];
        document.querySelectorAll('input[name="color"]:checked')
            .forEach(cb => colors.push(cb.value));
        	
            if (!min || !max) {
                   min = document.getElementById("minamount").value.replace("₹", "");
                   max = document.getElementById("maxamount").value.replace("₹", "");
               }
               let url = '/filterProducts?min=' + min + '&max=' + max;
	
               fetch(url)
                      .then(res => res.text())
                      .then(html => {
                          document.getElementById("productContainer").innerHTML = html;
                      })
                      .catch(err => console.error(err));
	
}*/


