package net.robinjam.aes.wsn;

import java.util.logging.Logger;

@javax.jws.WebService(
                      serviceName = "WS-BrokeredNotification",
                      targetNamespace = "http://docs.oasis-open.org/wsn/br-2",
                      wsdlLocation = "http://docs.oasis-open.org/wsn/brw-2.wsdl",
                      endpointInterface = "org.oasis_open.docs.wsn.brw_2.NotificationBroker")
                      
public class NotificationBroker implements org.oasis_open.docs.wsn.brw_2.NotificationBroker {

    private static final Logger LOG = Logger.getLogger(NotificationBroker.class.getName());

    /* (non-Javadoc)
     * @see org.oasis_open.docs.wsn.brw_2.NotificationBroker#notify(org.oasis_open.docs.wsn.b_2.Notify  notify )*
     */
    public void notify(org.oasis_open.docs.wsn.b_2.Notify notify) { 
        LOG.info("Executing operation notify");
        System.out.println(notify);
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.oasis_open.docs.wsn.brw_2.NotificationBroker#getCurrentMessage(org.oasis_open.docs.wsn.b_2.GetCurrentMessage  getCurrentMessageRequest )*
     */
    public org.oasis_open.docs.wsn.b_2.GetCurrentMessageResponse getCurrentMessage(org.oasis_open.docs.wsn.b_2.GetCurrentMessage getCurrentMessageRequest) throws org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault , org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault , org.oasis_open.docs.wsn.bw_2.NoCurrentMessageOnTopicFault , org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault , org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault , org.oasis_open.docs.wsn.bw_2.MultipleTopicsSpecifiedFault    { 
        LOG.info("Executing operation getCurrentMessage");
        System.out.println(getCurrentMessageRequest);
        try {
            org.oasis_open.docs.wsn.b_2.GetCurrentMessageResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault("ResourceUnknownFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault("InvalidTopicExpressionFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.NoCurrentMessageOnTopicFault("NoCurrentMessageOnTopicFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault("TopicExpressionDialectUnknownFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault("TopicNotSupportedFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.MultipleTopicsSpecifiedFault("MultipleTopicsSpecifiedFault...");
    }

    /* (non-Javadoc)
     * @see org.oasis_open.docs.wsn.brw_2.NotificationBroker#registerPublisher(org.oasis_open.docs.wsn.br_2.RegisterPublisher  registerPublisherRequest )*
     */
    public org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse registerPublisher(org.oasis_open.docs.wsn.br_2.RegisterPublisher registerPublisherRequest) throws org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault , org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault , org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault , org.oasis_open.docs.wsn.brw_2.PublisherRegistrationFailedFault , org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault , org.oasis_open.docs.wsn.brw_2.PublisherRegistrationRejectedFault    { 
        LOG.info("Executing operation registerPublisher");
        System.out.println(registerPublisherRequest);
        try {
            org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault("UnacceptableInitialTerminationTimeFault...");
        //throw new org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault("ResourceUnknownFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault("InvalidTopicExpressionFault...");
        //throw new PublisherRegistrationFailedFault("PublisherRegistrationFailedFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault("TopicNotSupportedFault...");
        //throw new PublisherRegistrationRejectedFault("PublisherRegistrationRejectedFault...");
    }

    /* (non-Javadoc)
     * @see org.oasis_open.docs.wsn.brw_2.NotificationBroker#subscribe(org.oasis_open.docs.wsn.b_2.Subscribe  subscribeRequest )*
     */
    public org.oasis_open.docs.wsn.b_2.SubscribeResponse subscribe(org.oasis_open.docs.wsn.b_2.Subscribe subscribeRequest) throws org.oasis_open.docs.wsn.bw_2.NotifyMessageNotSupportedFault , org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault , org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault , org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault , org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault , org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault , org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault , org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault , org.oasis_open.docs.wsn.bw_2.SubscribeCreationFailedFault , org.oasis_open.docs.wsn.bw_2.UnrecognizedPolicyRequestFault , org.oasis_open.docs.wsn.bw_2.InvalidFilterFault , org.oasis_open.docs.wsn.bw_2.UnsupportedPolicyRequestFault    { 
        LOG.info("Executing operation subscribe");
        System.out.println(subscribeRequest);
        try {
            org.oasis_open.docs.wsn.b_2.SubscribeResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new org.oasis_open.docs.wsn.bw_2.NotifyMessageNotSupportedFault("NotifyMessageNotSupportedFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault("UnacceptableInitialTerminationTimeFault...");
        //throw new org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault("ResourceUnknownFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault("InvalidTopicExpressionFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault("InvalidMessageContentExpressionFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault("TopicNotSupportedFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault("TopicExpressionDialectUnknownFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault("InvalidProducerPropertiesExpressionFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.SubscribeCreationFailedFault("SubscribeCreationFailedFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.UnrecognizedPolicyRequestFault("UnrecognizedPolicyRequestFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.InvalidFilterFault("InvalidFilterFault...");
        //throw new org.oasis_open.docs.wsn.bw_2.UnsupportedPolicyRequestFault("UnsupportedPolicyRequestFault...");
    }

}
