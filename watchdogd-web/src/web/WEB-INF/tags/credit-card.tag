<%@ taglib prefix="dog" tagdir="/WEB-INF/tags"%>

	<fieldset class="form">
				<script type="text/javascript" src="https://js.stripe.com/v1/"></script>
				<!-- jQuery is used only for this example; it isnt required to use Stripe -->
				<script type="text/javascript">
				    // this identifies your website in the createToken call below
				    Stripe.setPublishableKey('pk_lWOSTZ41XOaw1qkpij0cvEm13GsOK');

				    function stripeResponseHandler(status, response) {
					if (response.error) {
					    // re-enable the submit button
					    $('.submit-button').removeAttr("disabled");
					    // show the errors on the form
					    $(".payment-errors").html(response.error.message);
								$('.payment-errors').toggle();
					} else {
					    var form$ = $("#command");
					    // token contains id, last4, and card type
					    var token = response['id'];
					    // insert the token into the form so it gets submitted to the server
					    form$.append("<input type='hidden' name='stripeToken' value='" + token + "' />");
					    // and submit
					    form$.get(0).submit();
					}
				    }

				    $(document).ready(function() {
					$("#command").submit(function(event) {
					    // disable the submit button to prevent repeated clicks
					    $('.submit-button').attr("disabled", "disabled");
					    // createToken returns immediately - the supplied callback submits the form if there are no errors
					    Stripe.createToken({
						number: $('.card-number').val(),
						name: $('.card-name').val(),
						cvc: $('.card-cvc').val(),
						exp_month: $('.card-expiry-month').val(),
						exp_year: $('.card-expiry-year').val()
					    }, stripeResponseHandler);
					    return false; // submit from callback
					});
				    });

				    if (window.location.protocol === 'file:') {
					alert("stripe.js does not work when included in pages served over file:// URLs. Try serving this page over a webserver. Contact support@stripe.com if you need assistance.");
				    }
				</script>
					<legend>Credit Card</legend>
					<!-- to display errors returned by createToken -->
					<div class="payment-errors error" style="display:none;"></div>

					<dog:messages />

					    <ol>
						<li>
								<label>Full Card Holder Name</label>
								<input type="text" class="card-name" />
					    	</li>
							<li>
								<label>Card Number</label>
								<input type="text" size="20" autocomplete="off" class="card-number" />
					    	</li>
						<li>
								<label>CVC</label>
								<input type="text" size="4" autocomplete="off" class="card-cvc" />
					    	</li>
					    	<li>
								<label class="inline">Expiration (MM/YYYY)</label>
								<input type="text" size="2" class="card-expiry-month"/>
								<span> / </span>
								<input type="text" size="4" class="card-expiry-year"/>
					    	</li>
						<li>
								<label>Coupon</label>
								<input type="text" name="coupon" />
					    	</li>
						<li>
							<input type="submit" name="_save" value="Save" class="submit-button" />
						</li>
					</ol>
				</fieldset>
