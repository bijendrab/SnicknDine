var app = angular.module("myapp", []).controller(
    "myController",
    function ($scope, $http) {
        /*$scope.products = productsData;*/
        $scope.cart = [];
        $scope.total = 0;

        var BASE_PATH = "http://localhost:8080";

        $scope.getProductList = function () {
            $http.get(BASE_PATH + "/getProductsList")
                .success(function (data) {
                    $scope.products = data;
                });
        }

        $scope.addToCart = function (product) {
            $http.put(BASE_PATH + "/cart/add/" + product.productId)
                .success(function () {
                    /*alert("Added Successfully");*/
                })
            /*debugger*/
            if ($scope.cart.length === 0){
                product.count = 1;
                $scope.cart.push(product);
            } else {
                var repeat = false;
                for(var i = 0; i< $scope.cart.length; i++){
                    if($scope.cart[i].productId === product.productId){
                        repeat = true;
                        $scope.cart[i].count +=1;
                    }
                }
                if (!repeat) {
                    product.count = 1;
                    $scope.cart.push(product);
                }
            }
        }
        $scope.subFromCart = function (product) {
            if (product.count > -1) {
                product.count -= 1;
                /*var expireDate = new Date();
                expireDate.setDate(expireDate.getDate() + 1);
                $cookies.putObject('cart', $scope.cart, {'expires': expireDate});
                $scope.cart = $cookies.getObject('cart');*/
            }
            $http.put(BASE_PATH + "/cart/decreaseCartItem/"
                + product.productId).success(function () {
                /*alert("Added Successfully");*/
            });

        }
        $scope.removeFromCart = function (cartItemId) {
            $http.put(BASE_PATH + "/cart/removeCartItem/"
                + cartItemId).success(function () {
                $scope.refreshCart();
            });
        }

        $scope.disableItem = function (productId) {
            $http.put(BASE_PATH + "/admin/disable/" + productId)
                .success(function () {
                    /*alert("Disabled Successfully");*/
                    $scope.getProductList();
                })
        }

        $scope.enableItem = function (productId) {
            $http.put(BASE_PATH + "/admin/enable/" + productId)
                .success(function () {
                    /*alert("Enabled Successfully");*/
                    $scope.getProductList();
                })
        }

        $scope.refreshItemTable = function () {
            $http.get(BASE_PATH + "/getAllProducts")
                .success(function (data) {
                    $scope.products = data;
                });
        }
        $scope.refreshCart = function () {
            $http.get(BASE_PATH + "/cart/getCart/"
                + $scope.cartId).success(function (data) {

                $scope.carts = data;
            })
        }

        $scope.getCart = function (cartId) {
            $scope.cartId = cartId;
            $scope.refreshCart(cartId);
        }

        $scope.clearCart = function () {
            $http.put(BASE_PATH + "/cart/removeAllItems/"
                + $scope.cartId).success(function () {
                $scope.refreshCart();
            });
        }

        $scope.calculateGrandTotal = function () {
            var grandTotal = 0.0;
            for (var i = 0; i < $scope.carts.cartItem.length; i++)
                grandTotal = grandTotal + $scope.carts.cartItem[i].price;
            return grandTotal;

        }
    });
