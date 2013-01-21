package ar.com.oxen.nibiru.sample.module;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ar.com.oxen.nibiru.crud.manager.api.CrudAction;
import ar.com.oxen.nibiru.crud.manager.api.CrudActionExtension;
import ar.com.oxen.nibiru.crud.manager.api.CrudEntity;
import ar.com.oxen.nibiru.crud.manager.api.CrudFactory;
import ar.com.oxen.nibiru.crud.manager.api.CrudManager;
import ar.com.oxen.nibiru.crud.utils.AbstractCrudActionExtension;
import ar.com.oxen.nibiru.crud.utils.AbstractCrudModuleConfigurator;
import ar.com.oxen.nibiru.crud.utils.SimpleCrudAction;
import ar.com.oxen.nibiru.report.api.Report;
import ar.com.oxen.nibiru.report.jasper.JasperReport;
import ar.com.oxen.nibiru.sample.domain.Course;
import ar.com.oxen.nibiru.sample.domain.Student;
import ar.com.oxen.nibiru.sample.domain.Subject;
import ar.com.oxen.nibiru.ui.api.extension.SubMenuExtension;
import ar.com.oxen.nibiru.ui.utils.extension.SimpleSubMenuExtension;
import ar.com.oxen.nibiru.validation.api.Validator;
import ar.com.oxen.nibiru.validation.generic.NotEmptyValidator;
import ar.com.oxen.nibiru.validation.generic.RegexpValidator;
import ar.com.oxen.nibiru.mail.api.MailMessage;
import ar.com.oxen.nibiru.mail.api.MailService;

public class ModuleConfigurator extends AbstractCrudModuleConfigurator {
	private static final String MENU_EXTENSION = "ar.com.oxen.nibiru.menu.sample.crud";
	
	private CrudFactory crudFactory;
	private MailService mailService;
	private DataSource dataSource;

	@Override
	protected void configure() {
		CrudManager<Subject> subjectCrudManager = this.crudFactory
				.createCrudManager(Subject.class);
		CrudActionExtension<Subject> subjectCrudActionExtension = this.crudFactory
				.createDefaultCrudActionExtension(Subject.class);

		CrudManager<Course> courseCrudManager = this.crudFactory
				.createCrudManager(Course.class);
		CrudActionExtension<Course> courseCrudActionExtension = this.crudFactory
				.createDefaultCrudActionExtension(Course.class);

		CrudManager<Student> studentCrudManager = this.crudFactory
				.createCrudManager(Student.class);
		CrudActionExtension<Student> studentCrudActionExtension = this.crudFactory
				.createDefaultCrudActionExtension(Student.class);		
		
		this.registerExtension(new SimpleSubMenuExtension("sample.crud",
				MENU_EXTENSION, 1), "ar.com.oxen.nibiru.menu",
				SubMenuExtension.class);

		this.addCrudWithMenu("sample.crud.subject", MENU_EXTENSION,
				subjectCrudManager, subjectCrudActionExtension);

		this.addChildCrudWithMenu("editCourses", subjectCrudManager,
				"subject", courseCrudManager,
				courseCrudActionExtension);
		
		this.addCrudWithMenu("sample.crud.student", MENU_EXTENSION,
				studentCrudManager, studentCrudActionExtension);

		// per-entity action example
		this.registerActions(studentCrudManager,
				new AbstractCrudActionExtension<Student>(null) {

					@Override
					public List<CrudAction> getEntityActions(
							CrudEntity<Student> entity) {
						List<CrudAction> actions = new ArrayList<CrudAction>(1);

						String name = entity.getEntity().getName();

						if (name != null && name.length() > 0) {
							String first = name.substring(0, 1);
							if (first.equals(first.toLowerCase())) {
								actions.add(new SimpleCrudAction("capitalize",
										true, false, true, true, true, null));
							}
						}

						return actions;
					}

					@Override
					public CrudEntity<?> performEntityAction(CrudAction action,
							CrudEntity<Student> entity) {
						Student student = entity.getEntity();
						student.setName(student.getName().substring(0, 1)
								.toUpperCase()
								+ student.getName().substring(1));
						return entity;
					}

				});
		
		
		// email example
		this.registerActions(studentCrudManager,
				new AbstractCrudActionExtension<Student>(null) {

					@Override
					public List<CrudAction> getEntityActions(
							CrudEntity<Student> entity) {
						List<CrudAction> actions = new ArrayList<CrudAction>(1);

						String name = entity.getEntity().getName();

						if (name != null && name.indexOf("@") >= 0) {
							actions.add(new SimpleCrudAction("sendMail", true,
									false, true, false, true, null));
						}

						return actions;
					}

					@Override
					public CrudEntity<?> performEntityAction(CrudAction action,
							CrudEntity<Student> entity) {
						MailMessage message = new MailMessage("test@gmail.com",
								"Greets to " + entity.getEntity().getName(),
								"This is a test");
						message.getTo().add(entity.getEntity().getName());
						mailService.sendMail(message);
						return null;
					}

				});

		// Validation sample
		this.registerExtension(
				new RegexpValidator("^Mat.*", "subjectBeginning"),
				Subject.class.getName() + ".name", Validator.class);

		this.registerExtension(new NotEmptyValidator(), Subject.class.getName()
				+ ".description", Validator.class);

		// Report sample
		registerExtension(
				new JasperReport(this.getClass().getResourceAsStream(
						"/ar/com/oxen/nibiru/sample/report/myReport.jrxml"),
						this.dataSource), Report.EXTENSION_POINT_NAME,
				Report.class);
	}

	public void setCrudFactory(CrudFactory crudFactory) {
		this.crudFactory = crudFactory;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
