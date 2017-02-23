<%--
  Created by IntelliJ IDEA.
  User: BARCO
  Date: 08-Feb-17
  Time: 12:55 PM
  To change this template use File | Settings | File Templates.
--%>


    <div class="container">
        <div class="row">
            <div class="Absolute-Center is-Responsive">

                <div class="omb_login">
                    <h3 class="omb_authTitle">Login</h3>
                    <div class="row omb_row-sm-offset-3 omb_socialButtons">
                        <div class="col-xs-6 col-sm-3">
                            <form action="/signin/facebook" method="post">
                                <button type="submit" class="btn btn-lg btn-block omb_btn-facebook">
                                    <i class="fa fa-facebook visible-xs"></i>
                                    <span class="hidden-xs" style="color: white">Facebook</span>
                                </button>
                            </form>
                        </div>
                        <div class="col-xs-6 col-sm-3">
                            <form action="/signin/twitter" method="post">
                                <button type="submit" class="btn btn-lg btn-block omb_btn-twitter">
                                        <i class="fa fa-twitter visible-xs"></i>
                                     <span class="hidden-xs" style="color: white">Twitter</span>
                                </button>
                            </form>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
