package net.robinjam.aes.wsn;

import org.oasis_open.docs.wsn.b_2.GetCurrentMessage;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessageResponse;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.br_2.RegisterPublisher;
import org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationFailedFault;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationRejectedFault;
import org.oasis_open.docs.wsn.bw_2.InvalidFilterFault;
import org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
import org.oasis_open.docs.wsn.bw_2.MultipleTopicsSpecifiedFault;
import org.oasis_open.docs.wsn.bw_2.NoCurrentMessageOnTopicFault;
import org.oasis_open.docs.wsn.bw_2.NotifyMessageNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.SubscribeCreationFailedFault;
import org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault;
import org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault;
import org.oasis_open.docs.wsn.bw_2.UnrecognizedPolicyRequestFault;
import org.oasis_open.docs.wsn.bw_2.UnsupportedPolicyRequestFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

public class NotificationBroker implements org.oasis_open.docs.wsn.brw_2.NotificationBroker {

	@Override
	public void notify(Notify notify) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public GetCurrentMessageResponse getCurrentMessage(GetCurrentMessage getCurrentMessageRequest) throws ResourceUnknownFault, InvalidTopicExpressionFault, NoCurrentMessageOnTopicFault, TopicExpressionDialectUnknownFault, TopicNotSupportedFault, MultipleTopicsSpecifiedFault {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public RegisterPublisherResponse registerPublisher(RegisterPublisher registerPublisherRequest) throws UnacceptableInitialTerminationTimeFault, ResourceUnknownFault, InvalidTopicExpressionFault, PublisherRegistrationFailedFault, TopicNotSupportedFault, PublisherRegistrationRejectedFault {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public SubscribeResponse subscribe(Subscribe subscribeRequest) throws NotifyMessageNotSupportedFault, UnacceptableInitialTerminationTimeFault, ResourceUnknownFault, InvalidTopicExpressionFault, InvalidMessageContentExpressionFault, TopicNotSupportedFault, TopicExpressionDialectUnknownFault, InvalidProducerPropertiesExpressionFault, SubscribeCreationFailedFault, UnrecognizedPolicyRequestFault, InvalidFilterFault, UnsupportedPolicyRequestFault {
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
