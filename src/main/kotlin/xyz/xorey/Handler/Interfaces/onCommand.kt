import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.Category
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.interactions.commands.build.OptionData

interface onCommand  {
    fun getOptions() : List<OptionData>

    fun run(args: Array<String?>, channel: TextChannel, member: Member?, content: String, message: Message, category: Category?, event: MessageReceivedEvent)

    fun run(channel: TextChannel, member: Member?, event: SlashCommandInteractionEvent)
}