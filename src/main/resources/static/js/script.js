const paymentStart=()=>{
    console.log("payment started!!")
    let amount = $("#payment_field").val()
    console.log(amount)
    if (amount == '' || amount == null){
        alert("amount is required!!")
        return;
    }
    // using ajax to to send request to server to create order jquery
    $.ajax(
        {
            url:'/premium/create_order',
            data:JSON.stringify({amount:amount, info:'order_request'}),
            contentType:'application/json',
            type:'POST',
            dataType:'json',
            success:function (response){
            // on success
                console.log(response)
                if (response.status=='created'){
                    let options={
                        key:"rzp_test_N5w4vDygKhUrXc",
                        amount:response.amount,
                        currency:'INR',
                        name: 'acsxedu',
                        description:'Donation',
                        image:'https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Flcwd_logo.45da3818.png&w=1080&q=75',
                        order_id:response.id,
                        handler:function(response){
                            console.log(response.razorpay_payment_id)
                            console.log(response.razorpay_order_id)
                            console.log(response.log(razorpay_signature))
                            console.log("Payment successful")
                            alert("Congrats!! Payment success")
                    },
                        prefill:{
                            name: "",
                            email: "",
                            concat:"",
                        },
                        notes:{
                            address:"accessxedu",
                        },
                        theme:{
                            color:"#3399cc"
                        }
                    }
                }
            },
            error:function (error){
                // on error
                console.log(error)
                alert("something went wrong!!")
            },
        }
    )
    let rzp = new Razorpay(options);

    rzp.on("payment failed", function (response){
        alert(response.error.code)
        alert(response.error.description)
        alert(response.error.source)
        alert(response.error.step)
        alert(response.error.reason)
        alert(response.error.metadata.order_id)
        alert(response.error.metadata.payment_link_id)
        alert("Payment failed")

    })

    rzp.open()
}